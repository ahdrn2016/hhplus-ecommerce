package kr.hhplus.be.server.domain.coupon;

import java.util.List;

public interface IssuedCouponRepository {

    List<IssuedCoupon> findCouponsByUserId(Long userId);

    IssuedCoupon findCouponByUserIdAndCouponId(Long userId, Long couponId);

    IssuedCoupon save(IssuedCoupon issuedCoupon);

    int countByCouponId(Long couponId);

    IssuedCoupon findIssuedCoupon(Long issuedCouponId, IssuedCouponStatus status);
}
