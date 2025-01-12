package kr.hhplus.be.server.domain.coupon;

import kr.hhplus.be.server.interfaces.api.coupon.CouponResponse;

import java.util.List;
import java.util.stream.Collectors;

public class CouponInfo {
    public static List<CouponResponse.UserCouponDto> toResponse(List<UserCoupon> userCoupons) {
        return userCoupons.stream()
                .map(CouponResponse.UserCouponDto::of)
                .collect(Collectors.toList());
    }

    public static CouponResponse.UserCouponDto toResponse(UserCoupon userCoupon) {
        return CouponResponse.UserCouponDto.builder()
                .userId(userCoupon.getUserId())
                .couponId(userCoupon.getCouponId())
                .status(userCoupon.getStatus().name())
                .build();
    }

    public static CouponResponse.CouponDto toResponse(Coupon coupon) {
        return CouponResponse.CouponDto.builder()
                .couponId(coupon.getId())
                .name(coupon.getName())
                .amount(coupon.getAmount())
                .validStartDate(coupon.getValidStartDate())
                .validEndDate(coupon.getValidEndDate())
                .quantity(coupon.getQuantity())
                .build();
    }
}
