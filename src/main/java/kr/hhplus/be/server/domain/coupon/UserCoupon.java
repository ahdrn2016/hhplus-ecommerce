package kr.hhplus.be.server.domain.coupon;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kr.hhplus.be.server.domain.user.User;

public class UserCoupon {

    private User user;
    private Coupon coupon;

    @Enumerated(EnumType.STRING)
    private UserCouponStatus status;
}
