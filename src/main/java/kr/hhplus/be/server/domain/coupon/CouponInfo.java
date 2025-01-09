package kr.hhplus.be.server.domain.coupon;

import kr.hhplus.be.server.interfaces.api.coupon.CouponResponse;

import java.util.List;
import java.util.stream.Collectors;

public class CouponInfo {
    public static List<CouponResponse.Coupon> toResponse(List<UserCoupon> userCoupons) {
        return userCoupons.stream()
                .map(CouponResponse.Coupon::of)
                .collect(Collectors.toList());
    }

    public static CouponResponse.IssueCoupon toResponse(UserCoupon userCoupon) {
        return CouponResponse.IssueCoupon.builder()
                .couponId(userCoupon.getCoupon().getId())
                .name(userCoupon.getCoupon().getName())
                .amount(userCoupon.getCoupon().getAmount())
                .validStartDate(userCoupon.getCoupon().getValidStartDate())
                .validEndDate(userCoupon.getCoupon().getValidEndDate())
                .build();
    }

    public static CouponResponse.Coupon toResult(UserCoupon userCoupon) {
        return CouponResponse.Coupon.builder()
                .couponId(userCoupon.getCoupon().getId())
                .name(userCoupon.getCoupon().getName())
                .amount(userCoupon.getCoupon().getAmount())
                .validStartDate(userCoupon.getCoupon().getValidStartDate())
                .validEndDate(userCoupon.getCoupon().getValidEndDate())
                .build();
    }
}
