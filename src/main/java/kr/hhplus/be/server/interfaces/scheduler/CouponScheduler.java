package kr.hhplus.be.server.interfaces.scheduler;

import kr.hhplus.be.server.domain.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponScheduler {

    private final CouponService couponService;

    @Scheduled(fixedDelay = 5000)
    public void couponIssue() {
        couponService.issue();
    }
}
