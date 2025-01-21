package kr.hhplus.be.server.infrastructure.coupon;

import kr.hhplus.be.server.domain.coupon.IssuedCoupon;
import kr.hhplus.be.server.domain.coupon.IssuedCouponRepository;
import kr.hhplus.be.server.domain.coupon.IssuedCouponStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class IssuedCouponRepositoryImpl implements IssuedCouponRepository {

    private final IssuedCouponJpaRepository issuedCouponJpaRepository;

    @Override
    public List<IssuedCoupon> findCouponsByUserId(Long userId) {
        return issuedCouponJpaRepository.findAllByUserId(userId);
    }

    @Override
    public IssuedCoupon findCouponByUserIdAndCouponId(Long userId, Long couponId) {
        return issuedCouponJpaRepository.findCouponByUserIdAndCouponId(userId, couponId);
    }

    @Override
    public IssuedCoupon save(IssuedCoupon issuedCoupon) {
        return issuedCouponJpaRepository.save(issuedCoupon);
    }

    @Override
    public IssuedCoupon findByUserIdAndCouponIdAndStatus(Long userId, Long couponId, IssuedCouponStatus status) {
        return issuedCouponJpaRepository.findByUserIdAndCouponIdAndStatus(userId, couponId, status);
    }

}
