package kr.hhplus.be.server.infrastructure.coupon;

import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.coupon.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryImpl implements CouponRepository {

    private final CouponJpaRepository couponJpaRepository;
    private final CouponWaitingQueueRepository couponWaitingQueueRepository;

    @Override
    public Coupon findById(Long couponId) {
        return couponJpaRepository.findById(couponId).orElse(null);
    }

    @Override
    public Coupon save(Coupon coupon) {
        return couponJpaRepository.save(coupon);
    }

    @Override
    public boolean findIssuedCoupon(Long couponId, Long userId) {
        return couponWaitingQueueRepository.findIssuedCoupon(couponId, userId);
    }

    @Override
    public boolean addCouponRequest(Long couponId, Long userId) {
        return couponWaitingQueueRepository.addCouponRequest(couponId, userId);
    }

    @Override
    public void setCouponQuantity(Long couponId, int quantity) {
        couponWaitingQueueRepository.setCouponQuantity(couponId, quantity);
    }

    @Override
    public int getCouponQuantity(Long couponId) {
        return couponWaitingQueueRepository.getCouponQuantity(couponId);
    }

    @Override
    public void decrementCouponQuantity(Long couponId) {
        couponWaitingQueueRepository.decrementCouponQuantity(couponId);
    }

    @Override
    public Set<Long> getCouponIds() {
        return couponWaitingQueueRepository.getCouponIds();
    }

    @Override
    public Set<Long> getUserIds(Long couponId, int batchSize) {
        return couponWaitingQueueRepository.getUserIds(couponId, batchSize);
    }

    @Override
    public void setIssuedCoupon(Long couponId, Long userId) {
        couponWaitingQueueRepository.setIssuedCoupon(couponId, userId);
    }

    @Override
    public Long findIssuedCouponCount(Long couponId) {
        return couponWaitingQueueRepository.findIssuedCouponCount(couponId);
    }

    @Override
    public void deleteCouponIssue(Long couponId) {
        couponWaitingQueueRepository.deleteCouponIssue(couponId);
    }
}
