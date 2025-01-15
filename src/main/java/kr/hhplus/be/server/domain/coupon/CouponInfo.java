package kr.hhplus.be.server.domain.coupon;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CouponInfo {

    public static List<IssuedCoupon> of(List<kr.hhplus.be.server.domain.coupon.IssuedCoupon> issuedCoupons) {
        return issuedCoupons.stream()
                .map(IssuedCoupon::of)
                .collect(Collectors.toList());
    }

    public static IssuedCoupon of(kr.hhplus.be.server.domain.coupon.IssuedCoupon issuedCoupon) {
        return IssuedCoupon.builder()
                .userId(issuedCoupon.getUserId())
                .couponId(issuedCoupon.getCouponId())
                .status(issuedCoupon.getStatus().name())
                .build();
    }

    public static IssuedCoupon of(kr.hhplus.be.server.domain.coupon.IssuedCoupon userCoupon, int amount) {
        return IssuedCoupon.builder()
                .userId(userCoupon.getUserId())
                .couponId(userCoupon.getCouponId())
                .status(userCoupon.getStatus().name())
                .amount(amount)
                .build();
    }

    public record IssuedCoupon(
            Long userId,
            Long couponId,
            int amount,
            String status
    ) {
        @Builder
        public IssuedCoupon {}

        public static IssuedCoupon of(kr.hhplus.be.server.domain.coupon.IssuedCoupon issuedCoupon) {
            return IssuedCoupon.builder()
                    .userId(issuedCoupon.getUserId())
                    .couponId(issuedCoupon.getCouponId())
                    .status(issuedCoupon.getStatus().name())
                    .build();
        }
    }

}
