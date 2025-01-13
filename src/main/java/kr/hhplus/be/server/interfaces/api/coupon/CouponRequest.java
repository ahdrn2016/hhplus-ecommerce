package kr.hhplus.be.server.interfaces.api.coupon;

import kr.hhplus.be.server.application.coupon.CouponCriteria;

public class CouponRequest {

    public record CouponDto(Long userId, Long couponId) {

        public CouponCriteria.CouponDto toCriteria() {
            return CouponCriteria.CouponDto.builder()
                    .userId(userId)
                    .couponId(couponId)
                    .build();
        }
    }

}
