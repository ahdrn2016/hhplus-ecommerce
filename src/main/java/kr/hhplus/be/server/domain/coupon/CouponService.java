package kr.hhplus.be.server.domain.coupon;

import kr.hhplus.be.server.support.exception.CustomException;
import kr.hhplus.be.server.support.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final IssuedCouponRepository issuedCouponRepository;
    private final CouponRepository couponRepository;

    public List<CouponInfo.IssuedCoupon> coupons(Long userId) {
        List<IssuedCoupon> issuedCoupons = issuedCouponRepository.findCouponsByUserId(userId);
        return CouponInfo.of(issuedCoupons);
    }

    @Transactional
    public CouponInfo.IssuedCoupon issue(CouponCommand.Issue command) {
        IssuedCoupon issuedCoupon = issuedCouponRepository.findCouponByUserIdAndCouponId(command.userId(), command.couponId());
        if (issuedCoupon != null) throw new CustomException(ErrorCode.DUPLICATE_ISSUE_COUPON);

        Coupon coupon = couponRepository.findByIdWithLock(command.couponId());
        coupon.issue();

        IssuedCoupon savedIssuedCoupon = createIssuedCoupon(command.userId(), coupon);
        return CouponInfo.of(savedIssuedCoupon);
    }

    public CouponInfo.IssuedCoupon use(CouponCommand.Issue command) {
        IssuedCoupon issuedCoupon = issuedCouponRepository.findByUserIdAndCouponIdAndStatus(command.userId(), command.couponId(), IssuedCouponStatus.UNUSED);
        if (issuedCoupon == null) {
            throw new CustomException(ErrorCode.NO_AVAILABLE_COUPON);
        }
        issuedCoupon.used();
        return CouponInfo.of(issuedCoupon);
    }

    private IssuedCoupon createIssuedCoupon(Long userId, Coupon coupon) {
        IssuedCoupon issuedCoupon = IssuedCoupon.create(userId, coupon.getId(), coupon.getAmount(), IssuedCouponStatus.UNUSED);
        return issuedCouponRepository.save(issuedCoupon);
    }

}
