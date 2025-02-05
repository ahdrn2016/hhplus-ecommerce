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
    private final CouponCacheStorage couponCacheStorage;

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
        return couponCacheStorage.getIssuedCoupon(couponId, userId);
    }

    @Override
    public Long getIssuedCouponCount(Long couponId) {
        return couponCacheStorage.getIssuedCouponCount(couponId);
    }

    @Override
    public boolean addCouponRequest(Long couponId, Long userId) {
        return couponCacheStorage.addCouponRequest(couponId, userId);
    }

    @Override
    public void setCouponQuantity(Long couponId, int quantity) {
        couponCacheStorage.setCouponQuantity(couponId, quantity);
    }

    @Override
    public int getCouponQuantity(Long couponId) {
        return couponCacheStorage.getCouponQuantity(couponId);
    }

    @Override
    public void decrementCouponQuantity(Long couponId) {
        couponCacheStorage.decrementCouponQuantity(couponId);
    }

    @Override
    public Set<Long> getCouponIds() {
        return couponCacheStorage.getCouponIds();
    }

    @Override
    public Set<Long> getUserIds(Long couponId) {
        return couponCacheStorage.getUserIds(couponId);
    }

    @Override
    public void setIssuedCoupon(Long couponId, Long userId) {
        couponCacheStorage.setIssuedCoupon(couponId, userId);
    }
}
