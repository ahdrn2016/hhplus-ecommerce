package kr.hhplus.be.server.application.coupon;

import lombok.Builder;

public class CouponCriteria {

    public record CouponDto(
            Long userId,
            Long couponId
    ) {
        @Builder
        public CouponDto {}
    }

}
