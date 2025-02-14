package kr.hhplus.be.server.infrastructure.coupon;

import kr.hhplus.be.server.domain.coupon.IssuedCoupon;
import kr.hhplus.be.server.domain.coupon.IssuedCouponStatus;

public interface IssuedCouponRepositoryCustom {

    IssuedCoupon findIssuedCoupon(Long issuedCouponId, IssuedCouponStatus status);

}
