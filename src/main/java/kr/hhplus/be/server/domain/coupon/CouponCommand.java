package kr.hhplus.be.server.domain.coupon;

import lombok.Builder;

public class CouponCommand {

    public record Issue(
            Long userId,
            Long couponId
    ) {
        @Builder
        public Issue {}
    }

}
