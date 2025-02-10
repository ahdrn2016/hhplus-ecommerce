package kr.hhplus.be.server.interfaces.scheduler;

import kr.hhplus.be.server.domain.coupon.CouponProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponScheduler {

    private final CouponProcessor couponProcessor;

    @Scheduled(fixedDelay = 5000)
    public void couponIssue() {
        couponProcessor.processCouponIssue();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteCouponIssue() {
        couponProcessor.processDeleteCouponIssue();
    }
}
