package kr.hhplus.be.server.domain.coupon;

import kr.hhplus.be.server.interfaces.api.coupon.CouponResponse;
import kr.hhplus.be.server.interfaces.api.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final UserCouponRepository userCouponRepository;

    public List<UserResponse.UserCoupon> getCoupons(Long userId) {
        List<UserCoupon> userCoupons = userCouponRepository.findCouponsByUserId(userId);

        return null;
    }

    public CouponResponse.issueCoupon issueCoupon(Object command) {
        return null;
    }
}
