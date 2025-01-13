package kr.hhplus.be.server.interfaces.api.coupon;

import kr.hhplus.be.server.application.coupon.CouponResult;
import kr.hhplus.be.server.domain.coupon.CouponInfo;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

public class CouponResponse {

    public static List<UserCouponDto> of(List<CouponInfo.UserCouponDto> userCouponDtos) {
        return userCouponDtos.stream()
                .map(UserCouponDto::of)
                .collect(Collectors.toList());
    }

    public static UserCouponDto of(CouponResult.UserCouponDto userCouponDtos) {
        return UserCouponDto.builder()
                .userId(userCouponDtos.userId())
                .couponId(userCouponDtos.couponId())
                .status(userCouponDtos.status())
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

}
