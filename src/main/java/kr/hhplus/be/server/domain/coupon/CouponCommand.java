package kr.hhplus.be.server.domain.coupon;

import lombok.Builder;

public class CouponCommand {

    public record UserCouponDto(
            Long couponId,
            int amount
    ) {
        @Builder
        public UserCouponDto {}
    }
}
