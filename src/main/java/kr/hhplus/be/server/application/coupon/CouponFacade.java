package kr.hhplus.be.server.application.coupon;

import kr.hhplus.be.server.domain.coupon.CouponInfo;
import kr.hhplus.be.server.domain.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponFacade {

    private final CouponService couponService;

    public CouponResult.UserCouponDto issueCoupon(CouponCriteria.CouponDto criteria) {
        Long couponId = criteria.couponId();
        Long userId = criteria.userId();

        // 쿠폰 수량 차감
        couponService.issueCoupon(couponId);
        // 유저 쿠폰 생성
        CouponInfo.UserCouponDto userCoupon = couponService.createUserCoupon(userId, couponId);

        return CouponResult.of(userCoupon);
    }
}
