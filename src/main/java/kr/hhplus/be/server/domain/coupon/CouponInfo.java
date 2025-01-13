package kr.hhplus.be.server.domain.coupon;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CouponInfo {

    public static List<UserCouponDto> of(List<UserCoupon> userCoupons) {
        return userCoupons.stream()
                .map(UserCouponDto::of)
                .collect(Collectors.toList());
    }

    public static UserCouponDto of(UserCoupon userCoupon) {
        return UserCouponDto.builder()
                .userId(userCoupon.getUserId())
                .couponId(userCoupon.getCoupon().getId())
                .status(userCoupon.getStatus().name())
                .build();
    }

    public static CouponDto of(Coupon coupon) {
        return CouponDto.builder()
                .couponId(coupon.getId())
                .name(coupon.getName())
                .amount(coupon.getAmount())
                .validStartDate(coupon.getValidStartDate())
                .validEndDate(coupon.getValidEndDate())
                .quantity(coupon.getQuantity())
                .build();
    }

    public record UserCouponDto(
            Long userId,
            Long couponId,
            int amount,
            String status
    ) {
        @Builder
        public UserCouponDto {}

        public static UserCouponDto of(UserCoupon userCoupon) {
            return UserCouponDto.builder()
                    .userId(userCoupon.getUserId())
                    .couponId(userCoupon.getCoupon().getId())
                    .amount(userCoupon.getCoupon().getAmount())
                    .status(userCoupon.getStatus().name())
                    .build();
        }
    }

    public record CouponDto(
            Long couponId,
            String name,
            int amount,
            LocalDateTime validStartDate,
            LocalDateTime validEndDate,
            int quantity
    ) {
        @Builder
        public CouponDto {}
    }

}
