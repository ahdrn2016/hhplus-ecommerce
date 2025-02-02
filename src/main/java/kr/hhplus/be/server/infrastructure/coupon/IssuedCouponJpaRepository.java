package kr.hhplus.be.server.infrastructure.coupon;

import kr.hhplus.be.server.domain.coupon.IssuedCoupon;
import kr.hhplus.be.server.domain.coupon.IssuedCouponStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssuedCouponJpaRepository extends JpaRepository<IssuedCoupon, Long> {

    List<IssuedCoupon> findAllByUserId(Long userId);

    IssuedCoupon findCouponByUserIdAndCouponId(Long userId, Long couponId);

    IssuedCoupon findByUserIdAndCouponIdAndStatus(Long userId, Long couponId, IssuedCouponStatus status);

    int countByCouponId(Long couponId);
}
