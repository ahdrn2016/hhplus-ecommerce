package kr.hhplus.be.server.infrastructure.coupon;

import kr.hhplus.be.server.domain.coupon.UserCoupon;
import kr.hhplus.be.server.domain.coupon.UserCouponStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCouponJpaRepository extends JpaRepository<UserCoupon, Long> {

    List<UserCoupon> findAllByUserId(Long userId);

    UserCoupon findCouponByUserIdAndCouponId(Long userId, Long couponId);

    UserCoupon findByUserIdAndCouponIdAndStatus(Long userId, Long couponId, UserCouponStatus status);
}
