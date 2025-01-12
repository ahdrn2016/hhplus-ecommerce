package kr.hhplus.be.server.application.coupon;

import kr.hhplus.be.server.domain.coupon.CouponService;
import kr.hhplus.be.server.interfaces.api.coupon.CouponResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponFacade {

    private final CouponService couponService;

    public CouponResponse.UserCouponDto issueCoupon(CouponParam.CouponDto param) {
        Long couponId = param.couponId();
        Long userId = param.userId();

        // 쿠폰 수량 차감
        couponService.issueCoupon(couponId);
        // 유저 쿠폰 생성
        return couponService.createUserCoupon(userId, couponId);
    }
}
