package kr.hhplus.be.server.domain.coupon;

import kr.hhplus.be.server.support.exception.CustomException;
import kr.hhplus.be.server.support.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponService {

    private final IssuedCouponRepository issuedCouponRepository;
    private final CouponRepository couponRepository;
    private final RedissonClient redissonClient;

    public List<CouponInfo.IssuedCoupon> coupons(Long userId) {
        List<IssuedCoupon> issuedCoupons = issuedCouponRepository.findCouponsByUserId(userId);
        return CouponInfo.of(issuedCoupons);
    }

    @Transactional
    public CouponInfo.IssuedCoupon issue(CouponCommand.Issue command) throws InterruptedException {
        RLock lock = redissonClient.getLock("lock:coupon:" + command.couponId());

        try {
            boolean isLocked = lock.tryLock(5, 3, TimeUnit.SECONDS);
            if (!isLocked) {
                log.warn("Lock 획득 실패: {}", lock.getName());
                throw new IllegalThreadStateException("락 획득에 실패하였습니다.");
            }

            return issuedCoupon(command);

        } finally {
            if (lock.isHeldByCurrentThread()) { // 해당 세션에서 잠금 생성했는지 확인
                lock.unlock();
            }
        }
    }

    public CouponInfo.IssuedCoupon issuedCoupon(CouponCommand.Issue command) {
        IssuedCoupon issuedCoupon = issuedCouponRepository.findCouponByUserIdAndCouponId(command.userId(), command.couponId());
        if (issuedCoupon != null) throw new CustomException(ErrorCode.DUPLICATE_ISSUE_COUPON);

        Coupon coupon = couponRepository.findByIdWithLock(command.couponId());
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

    private IssuedCoupon createIssuedCoupon(Long userId, Coupon coupon) {
        IssuedCoupon issuedCoupon = IssuedCoupon.create(userId, coupon.getId(), coupon.getAmount(), IssuedCouponStatus.UNUSED);
        return issuedCouponRepository.save(issuedCoupon);
    }

}
