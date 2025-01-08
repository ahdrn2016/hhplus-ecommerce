package kr.hhplus.be.server.domain.coupon;

import kr.hhplus.be.server.interfaces.api.coupon.CouponResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final UserCouponRepository userCouponRepository;
    private final CouponRepository couponRepository;

    public List<CouponResponse.Coupons> getCoupons(Long userId) {
        List<UserCoupon> userCoupons = userCouponRepository.findCouponsByUserId(userId);
        return CouponInfo.toResponse(userCoupons);
    }

    public CouponResponse.issueCoupon issueCoupon(CouponCommand.IssueCoupon command) {
        Long userId = command.userId();
        Long couponId = command.couponId();

        // 유저 쿠폰 있는지 조회
        UserCoupon userCoupon = userCouponRepository.findCouponByUserIdAndCouponId(userId, couponId);
        userCoupon.duplicateCheck(userCoupon);

        // 쿠폰 선착순 재고
        Coupon coupon = couponRepository.findByIdWithLock(couponId);
        coupon.issue();

        UserCoupon result = userCouponRepository.save(userCoupon);

        return null;
    }
}
