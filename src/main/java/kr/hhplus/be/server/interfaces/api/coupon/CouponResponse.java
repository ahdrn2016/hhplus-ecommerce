package kr.hhplus.be.server.interfaces.api.coupon;

import kr.hhplus.be.server.domain.coupon.UserCoupon;
import lombok.Builder;

import java.time.LocalDateTime;

public class CouponResponse {

    public record UserCouponDto(Long userId, Long couponId, String status) {
        @Builder
        public UserCouponDto {}

        public static UserCouponDto of(UserCoupon userCoupon) {
            return UserCouponDto.builder()
                    .userId(userCoupon.getUserId())
                    .couponId(userCoupon.getCouponId())
                    .status(userCoupon.getStatus().name())
                    .build();
        }
    }

    public record CouponDto(Long couponId, String name, int amount, LocalDateTime validStartDate, LocalDateTime validEndDate, int quantity) {
        @Builder
        public CouponDto {}
    }

}
