package kr.hhplus.be.server.domain.coupon;

import kr.hhplus.be.server.interfaces.api.coupon.CouponResponse;
import kr.hhplus.be.server.interfaces.api.user.UserResponse;
import org.springframework.stereotype.Service;

@Service
public class CouponService {

    public UserResponse.UserCoupons getCoupons(Long userId) {
        return null;
    }

    public CouponResponse.issueCoupon issueCoupon(Object command) {
        return null;
    }
}
