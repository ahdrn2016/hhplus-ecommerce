package kr.hhplus.be.server.domain.coupon;

import kr.hhplus.be.server.interfaces.api.coupon.CouponResponse;

import java.util.List;

public interface UserCouponRepository {

    List<UserCoupon> findCouponsByUserId(Long userId);

    UserCoupon findCouponByUserIdAndCouponId(Long userId, Long couponId);

    UserCoupon save(UserCoupon userCoupon);
}
