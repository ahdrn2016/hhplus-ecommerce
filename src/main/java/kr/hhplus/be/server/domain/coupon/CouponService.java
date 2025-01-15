package kr.hhplus.be.server.domain.coupon;

import kr.hhplus.be.server.support.exception.CustomException;
import kr.hhplus.be.server.support.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final IssuedCouponRepository issuedCouponRepository;
    private final CouponRepository couponRepository;

    public List<CouponInfo.IssuedCoupon> coupons(Long userId) {
        List<IssuedCoupon> userCoupons = issuedCouponRepository.findCouponsByUserId(userId);
        return CouponInfo.of(userCoupons);
    }

    public CouponInfo.IssuedCoupon issue(CouponCommand.Issue command) {
        IssuedCoupon issuedCoupon = issuedCouponRepository.findCouponByUserIdAndCouponId(command.userId(), command.couponId());
        if (issuedCoupon != null) throw new CustomException(ErrorCode.DUPLICATE_ISSUE_COUPON);

        Coupon coupon = couponRepository.findByIdWithLock(command.couponId());
        coupon.issue();

        IssuedCoupon savedIssuedCoupon = createIssuedCoupon(command.userId(), command.couponId());
        return CouponInfo.of(savedIssuedCoupon);
    }

    public CouponInfo.IssuedCoupon use(CouponCommand.Issue command) {
        IssuedCoupon userCoupon = getUnusedUserCoupon(command.userId(), command.couponId());
        if (userCoupon == null) {
            throw new CustomException(ErrorCode.NO_AVAILABLE_COUPON);
        }
        userCoupon.used();

        Coupon coupon = couponRepository.findByIdWithLock(command.couponId());

        return CouponInfo.of(userCoupon, coupon.getAmount());
    }

    private IssuedCoupon createIssuedCoupon(Long userId, Long couponId) {
        IssuedCoupon issuedCoupon = IssuedCoupon.create(userId, couponId, IssuedCouponStatus.UNUSED);
        return issuedCouponRepository.save(issuedCoupon);
    }

    private IssuedCoupon getUnusedUserCoupon(Long userId, Long couponId) {
        return issuedCouponRepository.findByUserIdAndCouponIdAndStatus(userId, couponId, IssuedCouponStatus.UNUSED);
    }
}
