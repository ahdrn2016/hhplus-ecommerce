package kr.hhplus.be.server.domain.coupon;

import kr.hhplus.be.server.support.lock.DistributedLock;
import kr.hhplus.be.server.support.exception.CustomException;
import kr.hhplus.be.server.support.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponService {

    private final IssuedCouponRepository issuedCouponRepository;
    private final CouponRepository couponRepository;

    public Coupon createCoupon(Coupon coupon) {
        Coupon savedCoupon = couponRepository.save(coupon);
        couponRepository.setCouponQuantity(savedCoupon.getId(), savedCoupon.getQuantity());
        return savedCoupon;
    }

    public List<CouponInfo.IssuedCoupon> coupons(Long userId) {
        List<IssuedCoupon> issuedCoupons = issuedCouponRepository.findCouponsByUserId(userId);
        return CouponInfo.of(issuedCoupons);
    }

    @DistributedLock(key = "'lock:coupon:' + #command.couponId()")
    @Transactional
    public boolean issue(CouponCommand.Issue command) {
        // 잔여 수량 확인
        int quantity = couponRepository.getCouponQuantity(command.couponId());
        if (quantity <= 0) throw new CustomException(ErrorCode.SOLD_OUT_COUPON);

        // 발급 이력 확인
        boolean hasCoupon = couponRepository.findIssuedCoupon(command.couponId(), command.userId());
        if (hasCoupon) throw new CustomException(ErrorCode.DUPLICATE_ISSUE_COUPON);

        // Sorted Sets 저장
        boolean addRequest = couponRepository.addCouponRequest(command.couponId(), command.userId());
        if (addRequest) couponRepository.decrementCouponQuantity(command.couponId());

        return addRequest;
    }

    @Transactional
    public CouponInfo.IssuedCoupon use(Long issuedCouponId) {
        IssuedCoupon issuedCoupon = issuedCouponRepository.findIssuedCoupon(issuedCouponId, IssuedCouponStatus.UNUSED);
        if (issuedCoupon == null) {
            throw new CustomException(ErrorCode.NO_AVAILABLE_COUPON);
        }
        issuedCoupon.used();
        return CouponInfo.of(issuedCoupon);
    }

    public IssuedCoupon createIssuedCoupon(Long userId, Coupon coupon) {
        IssuedCoupon issuedCoupon = IssuedCoupon.create(userId, coupon.getId(), coupon.getAmount(), IssuedCouponStatus.UNUSED);
        return issuedCouponRepository.save(issuedCoupon);
    }

}
