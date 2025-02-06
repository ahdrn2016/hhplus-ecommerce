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
    public CouponInfo.IssuedCoupon issue(CouponCommand.Issue command) {
        IssuedCoupon issuedCoupon = issuedCouponRepository.findCouponByUserIdAndCouponId(command.userId(), command.couponId());
        if (issuedCoupon != null) throw new CustomException(ErrorCode.DUPLICATE_ISSUE_COUPON);

        Coupon coupon = couponRepository.findById(command.couponId());
        coupon.issue();

        IssuedCoupon savedIssuedCoupon = createIssuedCoupon(command.userId(), coupon);
        return CouponInfo.of(savedIssuedCoupon);
    }

    @Transactional
    public CouponInfo.IssuedCoupon use(CouponCommand.Issue command) {
        IssuedCoupon issuedCoupon = issuedCouponRepository.findByUserIdAndCouponIdAndStatus(command.userId(), command.couponId(), IssuedCouponStatus.UNUSED);
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

    @DistributedLock(key = "'lock:coupon:' + #command.couponId()")
    public boolean couponIssue(CouponCommand.Issue command) {
        // 잔여 수량 확인
        int quantity = couponRepository.getCouponQuantity(command.couponId());
        if (quantity <= 0) throw new CustomException(ErrorCode.SOLD_OUT_COUPON);

        // 발급 이력 확인
        boolean hasCoupon = couponRepository.getIssuedCoupon(command.couponId(), command.userId());
        if (hasCoupon) throw new CustomException(ErrorCode.DUPLICATE_ISSUE_COUPON);

        // Sorted Sets 저장
        boolean addRequest = couponRepository.addCouponRequest(command.couponId(), command.userId());
        if (addRequest) couponRepository.decrementCouponQuantity(command.couponId());

        return addRequest;
    }

    public void issue() {
        Set<Long> couponIds = couponRepository.getCouponIds();
        for (Long couponId : couponIds) {

            Coupon coupon = couponRepository.findById(couponId);
            Long issuedCouponCount = couponRepository.getIssuedCouponCount(couponId);
            while (coupon.getQuantity() > issuedCouponCount) { // DB 쿠폰 수량과 redis 발급 이력 수량 비교

                Set<Long> userIds = couponRepository.getUserIds(couponId);
                if (userIds.isEmpty()) break; // 유저 없으면 종료
                for(Long userId : userIds) {
                    createIssuedCoupon(userId, coupon); // 쿠폰 이력 DB 저장
                    couponRepository.setIssuedCoupon(couponId, userId); // redis 발급 이력에 저장
                }
            }
        }
    }
}
