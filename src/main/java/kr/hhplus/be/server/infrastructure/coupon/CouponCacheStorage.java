package kr.hhplus.be.server.infrastructure.coupon;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CouponCacheStorage {

    private final StringRedisTemplate redisTemplate;
    private static final String COUPON_QUANTITY_KEY = "coupon_quantity:";
    private static final String COUPON_REQUEST_KEY = "coupon_request:";
    private static final String ISSUED_COUPON_KEY = "issued_coupon:";

    public void setCouponQuantity(Long couponId, int quantity) {
        redisTemplate.opsForValue().set(COUPON_QUANTITY_KEY + couponId, String.valueOf(quantity));
    }

    public int getCouponQuantity(Long couponId) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(COUPON_QUANTITY_KEY + couponId))
                .map(Integer::valueOf)
                .orElse(0);
    }

    public boolean getIssuedCoupon(Long couponId, Long userId) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(ISSUED_COUPON_KEY + couponId, String.valueOf(userId)));
    }

    public Long getIssuedCouponCount(Long couponId) {
        return redisTemplate.opsForSet().size(ISSUED_COUPON_KEY + couponId);
    }

    public boolean addCouponRequest(Long couponId, Long userId) {
        return Boolean.TRUE.equals(redisTemplate.opsForZSet().add(COUPON_REQUEST_KEY + couponId, String.valueOf(userId), System.currentTimeMillis()));
    }

    public void decrementCouponQuantity(Long couponId) {
        redisTemplate.opsForValue().decrement(COUPON_QUANTITY_KEY + couponId);
    }

    public Set<Long> getCouponIds() {
        return redisTemplate.keys(COUPON_REQUEST_KEY + "*").stream()
                .map(key -> key.replace(COUPON_REQUEST_KEY, ""))
                .map(Long::valueOf)
                .collect(Collectors.toSet());
    }

    public Set<Long> getUserIds(Long couponId) {
        return redisTemplate.opsForZSet().popMin(COUPON_REQUEST_KEY + couponId, 10).stream()
                .map(ZSetOperations.TypedTuple::getValue)
                .map(Long::valueOf)
                .collect(Collectors.toSet());
    }

    public void setIssuedCoupon(Long couponId, Long userId) {
        redisTemplate.opsForSet().add(ISSUED_COUPON_KEY + couponId, String.valueOf(userId));
    }
}
