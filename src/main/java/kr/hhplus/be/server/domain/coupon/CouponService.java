package kr.hhplus.be.server.domain.coupon;

import kr.hhplus.be.server.interfaces.api.coupon.CouponResponse;
import kr.hhplus.be.server.support.exception.CustomException;
import kr.hhplus.be.server.support.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final UserCouponRepository userCouponRepository;
    private final CouponRepository couponRepository;

    public List<CouponResponse.UserCouponDto> getCoupons(Long userId) {
        List<UserCoupon> userCoupons = userCouponRepository.findCouponsByUserId(userId);
        return CouponInfo.toResponse(userCoupons);
    }

    public CouponResponse.CouponDto issueCoupon(Long couponId) {
        Coupon coupon = couponRepository.findByIdWithLock(couponId);
        coupon.issue();
        return CouponInfo.toResponse(coupon);
    }

    public CouponResponse.UserCouponDto createUserCoupon(Long userId, Long couponId) {
        getUserCoupon(userId, couponId);
        UserCoupon userCoupon = UserCoupon.create(userId, couponId, UserCouponStatus.UNUSED);
        UserCoupon savedUserCoupon = userCouponRepository.save(userCoupon);
        return CouponInfo.toResponse(savedUserCoupon);
    }

    private void getUserCoupon(Long userId, Long couponId) {
        UserCoupon userCoupon = userCouponRepository.findCouponByUserIdAndCouponId(userId, couponId);
        if (userCoupon != null) {
            throw new CustomException(ErrorCode.DUPLICATE_ISSUE_COUPON);
        }
    }

    public CouponResponse.UserCouponDto useCoupon(Long userId, Long couponId) {
        UserCoupon userCoupon = getUnusedUserCoupon(userId, couponId);
        if (userCoupon == null) {
            throw new CustomException(ErrorCode.NO_AVAILABLE_COUPON);
        }
        userCoupon.used();
        return CouponInfo.toResponse(userCoupon);
    }

    private UserCoupon getUnusedUserCoupon(Long userId, Long couponId) {
        return userCouponRepository.findByUserIdAndCouponIdAndStatus(userId, couponId, UserCouponStatus.UNUSED);
    }
}
