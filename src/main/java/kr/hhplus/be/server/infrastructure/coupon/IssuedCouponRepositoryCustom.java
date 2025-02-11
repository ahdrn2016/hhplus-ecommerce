package kr.hhplus.be.server.infrastructure.coupon;

import kr.hhplus.be.server.domain.coupon.IssuedCoupon;
import kr.hhplus.be.server.domain.coupon.IssuedCouponStatus;

public interface IssuedCouponRepositoryCustom {

    IssuedCoupon getIssuedCoupon(Long userId, Long couponId, IssuedCouponStatus status);

}
