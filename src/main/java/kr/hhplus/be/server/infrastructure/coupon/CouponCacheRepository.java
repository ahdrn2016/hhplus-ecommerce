package kr.hhplus.be.server.infrastructure.coupon;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponCacheRepository {

    private final StringRedisTemplate redisTemplate;
    private static final String COUPON_REQUEST_KEY = "coupon_request";

    public void addCouponRequest(Long couponId, Long userId) {
        redisTemplate.opsForZSet().add(COUPON_REQUEST_KEY + couponId, String.valueOf(userId), System.currentTimeMillis());
    }
}
