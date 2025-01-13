package kr.hhplus.be.server.application.coupon;

import kr.hhplus.be.server.domain.coupon.CouponInfo;
import kr.hhplus.be.server.domain.coupon.UserCoupon;
import lombok.Builder;

public class CouponResult {
    public static UserCouponDto of(CouponInfo.UserCouponDto userCoupon) {
        return UserCouponDto.builder()
                .userId(userCoupon.userId())
                .couponId(userCoupon.couponId())
                .status(userCoupon.status())
                .build();
    }

    public record UserCouponDto(Long userId, Long couponId, String status) {
        @Builder
        public UserCouponDto {}

        public static UserCouponDto of(UserCoupon userCoupon) {
            return UserCouponDto.builder()
                    .userId(userCoupon.getUserId())
                    .couponId(userCoupon.getCoupon().getId())
                    .status(userCoupon.getStatus().name())
                    .build();
        }
    }
}
