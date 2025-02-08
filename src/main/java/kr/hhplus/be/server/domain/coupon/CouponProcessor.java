package kr.hhplus.be.server.domain.coupon;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class CouponProcessor {

    private static final int BATCH_SIZE = 10;
    private final CouponRepository couponRepository;
    private final CouponService couponService;


    @Transactional
    public void processCouponIssue() {
        Set<Long> couponIds = couponRepository.getCouponIds();
        for (Long couponId : couponIds) {
            Coupon coupon = couponRepository.findById(couponId);
            Set<Long> userIds = couponRepository.getUserIds(couponId, BATCH_SIZE);
            if (CollectionUtils.isEmpty(userIds)) break;
            for(Long userId : userIds) {
                couponService.createIssuedCoupon(userId, coupon); // 쿠폰 이력 DB 저장
                couponRepository.setIssuedCoupon(couponId, userId); // redis 발급 이력에 저장
            }
        }
    }

}
