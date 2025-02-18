package kr.hhplus.be.server.interfaces.scheduler;

import kr.hhplus.be.server.domain.coupon.CouponWaitingQueue;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponScheduler {

    private final CouponWaitingQueue couponWaitingQueue;

    @Scheduled(fixedDelay = 5000)
    public void couponIssue() {
        couponWaitingQueue.couponIssue();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteCouponWaitingQueue() {
        couponWaitingQueue.deleteCouponWaitingQueue();
    }
}
