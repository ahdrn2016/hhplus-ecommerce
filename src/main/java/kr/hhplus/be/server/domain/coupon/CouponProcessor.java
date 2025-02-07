package kr.hhplus.be.server.domain.coupon;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class CouponProcessor {

    private final CouponRepository couponRepository;
    private final CouponService couponService;

    @Transactional
    public void processCouponIssue() {
        Set<Long> couponIds = couponRepository.getCouponIds();
        for (Long couponId : couponIds) {

            Coupon coupon = couponRepository.findById(couponId);
            Long issuedCouponCount = couponRepository.getIssuedCouponCount(couponId);
            while (coupon.getQuantity() > issuedCouponCount) { // DB 쿠폰 수량과 redis 발급 이력 수량 비교

                Set<Long> userIds = couponRepository.getUserIds(couponId);
                if (userIds.isEmpty()) break;
                for(Long userId : userIds) {
                    couponService.createIssuedCoupon(userId, coupon); // 쿠폰 이력 DB 저장
                    couponRepository.setIssuedCoupon(couponId, userId); // redis 발급 이력에 저장
                }
            }
        }
    }

}
