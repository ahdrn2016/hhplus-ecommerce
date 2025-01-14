package kr.hhplus.be.server.domain.coupon;

import java.util.List;

public interface UserCouponRepository {

    List<UserCoupon> findCouponsByUserId(Long userId);

    UserCoupon findCouponByUserIdAndCouponId(Long userId, Long couponId);

    UserCoupon save(UserCoupon userCoupon);

    UserCoupon findByUserIdAndCouponIdAndStatus(Long userId, Long couponId, UserCouponStatus status);
}
