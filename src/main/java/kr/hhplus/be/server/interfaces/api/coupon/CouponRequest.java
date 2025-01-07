package kr.hhplus.be.server.interfaces.api.coupon;

import kr.hhplus.be.server.domain.coupon.CouponCommand;

public class CouponRequest {

    public record issueCoupon(Long userId, Long couponId) {

        public CouponCommand.IssueCoupon toCommand() {
            return CouponCommand.IssueCoupon.builder()
                    .userId(userId)
                    .couponId(couponId)
                    .build();
        }
    }

}
