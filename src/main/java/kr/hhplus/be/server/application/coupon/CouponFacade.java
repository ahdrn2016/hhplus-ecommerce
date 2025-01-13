package kr.hhplus.be.server.application.coupon;

import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.coupon.CouponInfo;
import kr.hhplus.be.server.domain.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CouponFacade {

    private final CouponService couponService;

    @Transactional
    public CouponResult.UserCouponDto issueCoupon(CouponCriteria.CouponDto criteria) {
        Long couponId = criteria.couponId();
        Long userId = criteria.userId();

        // 쿠폰 수량 차감
        Coupon coupon = couponService.issueCoupon(couponId);
        // 유저 쿠폰 생성
        CouponInfo.UserCouponDto userCoupon = couponService.createUserCoupon(userId, coupon);

        return CouponResult.of(userCoupon);
    }
}
