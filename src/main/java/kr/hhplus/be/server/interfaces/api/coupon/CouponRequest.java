package kr.hhplus.be.server.interfaces.api.coupon;

import kr.hhplus.be.server.domain.coupon.CouponCommand;

public class CouponRequest {

    public record Issue(Long userId, Long couponId) {

        public CouponCommand.Issue toCommand() {
            return CouponCommand.Issue.builder()
                    .userId(userId)
                    .couponId(couponId)
                    .build();
        }
    }

}
