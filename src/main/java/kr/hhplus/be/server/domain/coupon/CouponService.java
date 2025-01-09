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

    public List<CouponResponse.Coupon> getCoupons(Long userId) {
        List<UserCoupon> userCoupons = userCouponRepository.findCouponsByUserId(userId);
        return CouponInfo.toResponse(userCoupons);
    }

    public CouponResponse.IssueCoupon issueCoupon(CouponCommand.IssueCoupon command) {
        Long userId = command.userId();
        Long couponId = command.couponId();

        UserCoupon userCoupon = createUserCoupon(userId, couponId);
        UserCoupon savedUserCoupon = userCouponRepository.save(userCoupon);
        return CouponInfo.toResponse(savedUserCoupon);
    }

    private UserCoupon createUserCoupon(Long userId, Long couponId) {
        UserCoupon userCoupon = userCouponRepository.findCouponByUserIdAndCouponId(userId, couponId);
        if (userCoupon != null) {
            throw new CustomException(ErrorCode.DUPLICATE_ISSUE_COUPON);
        }

        Coupon coupon = couponRepository.findByIdWithLock(couponId);
        coupon.issue();

        return UserCoupon.create(coupon);
    }

    public CouponResponse.Coupon useCoupon(Long userId, Long couponId) {
        UserCoupon userCoupon = getUserCoupon(userId, couponId);
        userCoupon.used();
        return CouponInfo.toResult(userCoupon);
    }

    private UserCoupon getUserCoupon(Long userId, Long couponId) {
        return userCouponRepository.findByUserIdAndCouponIdAndStatus(userId, couponId, UserCouponStatus.UNUSED);
    }
}
