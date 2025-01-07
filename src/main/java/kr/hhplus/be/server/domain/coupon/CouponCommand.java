package kr.hhplus.be.server.domain.coupon;

import lombok.Builder;

public class CouponCommand {

    public record IssueCoupon(Long userId, Long couponId) {
        @Builder
        public IssueCoupon {}
    }
}
