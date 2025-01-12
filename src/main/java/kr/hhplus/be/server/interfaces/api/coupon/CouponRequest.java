package kr.hhplus.be.server.interfaces.api.coupon;

import kr.hhplus.be.server.application.coupon.CouponParam;

public class CouponRequest {

    public record CouponDto(Long userId, Long couponId) {

        public CouponParam.CouponDto toParam() {
            return CouponParam.CouponDto.builder()
                    .userId(userId)
                    .couponId(couponId)
                    .build();
        }
    }

}
