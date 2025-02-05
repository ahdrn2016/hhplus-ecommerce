package kr.hhplus.be.server.infrastructure.coupon;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponCacheStorage {

    private final StringRedisTemplate redisTemplate;
    private static final String COUPON_REQUEST_KEY = "coupon_request";
    private static final String ISSUED_COUPON_KEY = "issued_coupon";

    public boolean getIssuedCoupon(Long couponId, Long userId) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(ISSUED_COUPON_KEY + couponId, String.valueOf(userId)));
    }

    public boolean addCouponRequest(Long couponId, Long userId) {
        return Boolean.TRUE.equals(redisTemplate.opsForZSet().add(COUPON_REQUEST_KEY + couponId, String.valueOf(userId), System.currentTimeMillis()));
    }

}
