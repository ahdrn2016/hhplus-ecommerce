package kr.hhplus.be.server.domain.coupon;

import kr.hhplus.be.server.interfaces.api.coupon.CouponResponse;

import java.util.List;
import java.util.stream.Collectors;

public class CouponInfo {
    public static List<CouponResponse.Coupons> toResponse(List<UserCoupon> userCoupons) {
        return userCoupons.stream()
                .map(CouponResponse.Coupons::of)
                .collect(Collectors.toList());
    }
}
