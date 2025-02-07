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
    private final CouponCacheRepository CouponCacheRepository;

    @Override
    public Coupon findById(Long couponId) {
        return couponJpaRepository.findById(couponId).orElse(null);
    }

    @Override
    public Coupon save(Coupon coupon) {
        return couponJpaRepository.save(coupon);
    }

    @Override
    public boolean getIssuedCoupon(Long couponId, Long userId) {
        return CouponCacheRepository.getIssuedCoupon(couponId, userId);
    }

    @Override
    public Long getIssuedCouponCount(Long couponId) {
        return CouponCacheRepository.getIssuedCouponCount(couponId);
    }

    @Override
    public boolean addCouponRequest(Long couponId, Long userId) {
        return CouponCacheRepository.addCouponRequest(couponId, userId);
    }

    @Override
    public void setCouponQuantity(Long couponId, int quantity) {
        CouponCacheRepository.setCouponQuantity(couponId, quantity);
    }

    @Override
    public int getCouponQuantity(Long couponId) {
        return CouponCacheRepository.getCouponQuantity(couponId);
    }

    @Override
    public void decrementCouponQuantity(Long couponId) {
        CouponCacheRepository.decrementCouponQuantity(couponId);
    }

    @Override
    public Set<Long> getCouponIds() {
        return CouponCacheRepository.getCouponIds();
    }

    @Override
    public Set<Long> getUserIds(Long couponId, int batchSize) {
        return CouponCacheRepository.getUserIds(couponId, batchSize);
    }

    @Override
    public void setIssuedCoupon(Long couponId, Long userId) {
        CouponCacheRepository.setIssuedCoupon(couponId, userId);
    }
}
