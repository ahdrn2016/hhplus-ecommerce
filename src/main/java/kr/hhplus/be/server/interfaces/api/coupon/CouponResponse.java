package kr.hhplus.be.server.interfaces.api.coupon;

import kr.hhplus.be.server.application.coupon.CouponResult;
import kr.hhplus.be.server.domain.coupon.CouponInfo;
import kr.hhplus.be.server.domain.coupon.UserCoupon;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CouponResponse {

    public static List<UserCouponDto> of(List<CouponInfo.UserCouponDto> couponInfo) {
        return couponInfo.stream()
                .map(UserCouponDto::of)
                .collect(Collectors.toList());
    }

    public static UserCouponDto of(CouponResult.UserCouponDto couponInfo) {
        return UserCouponDto.builder()
                .userId(couponInfo.userId())
                .couponId(couponInfo.couponId())
                .status(couponInfo.status())
                .build();
    }

    public record UserCouponDto(Long userId, Long couponId, String status) {
        @Builder
        public UserCouponDto {}

        public static UserCouponDto of(CouponInfo.UserCouponDto userCouponDto) {
            return UserCouponDto.builder()
                    .userId(userCouponDto.userId())
                    .couponId(userCouponDto.couponId())
                    .status(userCouponDto.status())
                    .build();
        }
    }

    public record CouponDto(Long couponId, String name, int amount, LocalDateTime validStartDate, LocalDateTime validEndDate, int quantity) {
        @Builder
        public CouponDto {}
    }

}
