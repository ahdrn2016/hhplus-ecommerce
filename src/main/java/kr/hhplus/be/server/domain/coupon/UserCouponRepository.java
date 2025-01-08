package kr.hhplus.be.server.domain.coupon;

import java.util.List;

public interface UserCouponRepository {

    List<UserCoupon> findCouponsByUserId(Long userId);

}
