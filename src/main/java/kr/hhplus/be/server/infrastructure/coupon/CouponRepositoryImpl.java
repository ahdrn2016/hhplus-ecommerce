package kr.hhplus.be.server.infrastructure.coupon;

import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.coupon.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
    public boolean addCouponRequest(Long couponId, Long userId) {
        return couponCacheStorage.addCouponRequest(couponId, userId);
    }
}
