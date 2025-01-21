package kr.hhplus.be.server.interfaces.api.coupon;

import kr.hhplus.be.server.domain.coupon.CouponInfo;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

public class CouponResponse {

    public static List<IssuedCoupon> of(List<CouponInfo.IssuedCoupon> issuedCoupons) {
        return issuedCoupons.stream()
                .map(IssuedCoupon::of)
                .collect(Collectors.toList());
    }

    public static IssuedCoupon of(CouponInfo.IssuedCoupon issuedCoupon) {
        return IssuedCoupon.builder()
                .userId(issuedCoupon.userId())
                .couponId(issuedCoupon.couponId())
                .status(issuedCoupon.status())
                .build();
    }

    public record IssuedCoupon(Long userId, Long couponId, String status) {
        @Builder
        public IssuedCoupon {}

        public static IssuedCoupon of(CouponInfo.IssuedCoupon issuedCoupon) {
            return IssuedCoupon.builder()
                    .userId(issuedCoupon.userId())
                    .couponId(issuedCoupon.couponId())
                    .status(issuedCoupon.status())
                    .build();
        }
    }

}
