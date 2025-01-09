package kr.hhplus.be.server.interfaces.api.coupon;

import kr.hhplus.be.server.domain.coupon.UserCoupon;
import lombok.Builder;

import java.time.LocalDateTime;

public class CouponResponse {

    public record Coupons(Long couponId, String name, int amount, LocalDateTime validStartDate, LocalDateTime validEndDate, String status) {
        @Builder
        public Coupons {}

        public static Coupons of(UserCoupon userCoupon) {
            return Coupons.builder()
                    .couponId(userCoupon.getId())
                    .name(userCoupon.getCoupon().getName())
                    .amount(userCoupon.getCoupon().getAmount())
                    .validStartDate(userCoupon.getCoupon().getValidStartDate())
                    .validEndDate(userCoupon.getCoupon().getValidEndDate())
                    .status(userCoupon.getStatus().name())
                    .build();
        }
    }

    public record IssueCoupon(Long couponId, String name, int amount, LocalDateTime validStartDate, LocalDateTime validEndDate) {
        @Builder
        public IssueCoupon {}
    }

}
