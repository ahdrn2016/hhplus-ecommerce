package kr.hhplus.be.server.domain.coupon;

import kr.hhplus.be.server.interfaces.api.user.UserResponse;

import java.util.List;

public class CouponInfo {

    public static UserResponse.UserCoupon toResponse(List<UserCoupon> userCoupons) {
        Long userId = userCoupons.get(0).getUser().getId();
        return UserResponse.UserCoupon.builder()
                .userId(userId)
//                .userCoupons(userCoupons)
                .build();
    }

}
