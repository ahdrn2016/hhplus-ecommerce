package kr.hhplus.be.server.interfaces.api.coupon;

import kr.hhplus.be.server.domain.coupon.CouponCommand;
import kr.hhplus.be.server.domain.coupon.UserCoupon;
import lombok.Builder;

import java.time.LocalDateTime;

public class CouponResponse {

    public record Coupon(Long couponId, String name, int amount, LocalDateTime validStartDate, LocalDateTime validEndDate, String status) {
        @Builder
        public Coupon {}

        public static Coupon of(UserCoupon userCoupon) {
            return Coupon.builder()
                    .couponId(userCoupon.getId())
                    .name(userCoupon.getCoupon().getName())
                    .amount(userCoupon.getCoupon().getAmount())
                    .validStartDate(userCoupon.getCoupon().getValidStartDate())
                    .validEndDate(userCoupon.getCoupon().getValidEndDate())
                    .status(userCoupon.getStatus().name())
                    .build();
        }

        public CouponCommand.OrderCoupon toCommand() {
            return CouponCommand.OrderCoupon.builder()
                    .couponId(couponId)
                    .amount(amount)
                    .build();
        }
    }

    public record IssueCoupon(Long couponId, String name, int amount, LocalDateTime validStartDate, LocalDateTime validEndDate) {
        @Builder
        public IssueCoupon {}
    }

}
