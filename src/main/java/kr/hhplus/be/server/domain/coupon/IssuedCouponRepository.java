package kr.hhplus.be.server.domain.coupon;

import java.util.List;

public interface IssuedCouponRepository {

    List<IssuedCoupon> findCouponsByUserId(Long userId);

    IssuedCoupon findCouponByUserIdAndCouponId(Long userId, Long couponId);

    IssuedCoupon save(IssuedCoupon userCoupon);

    IssuedCoupon findByUserIdAndCouponIdAndStatus(Long userId, Long couponId, IssuedCouponStatus status);
}
