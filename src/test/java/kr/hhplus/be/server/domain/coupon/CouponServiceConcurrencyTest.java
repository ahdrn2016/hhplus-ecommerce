package kr.hhplus.be.server.domain.coupon;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CouponServiceConcurrencyTest {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponWaitingQueue couponWaitingQueue;

    @Autowired
    private IssuedCouponRepository issuedCouponRepository;

    @Test
    void 쿠폰_수량_30개일_때_유저_40명이_동시에_쿠폰_발급_요청_시_30명만_성공한다() throws InterruptedException {
        // given
        LocalDateTime validStartDate = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime validEndDate = LocalDateTime.of(2025, 12, 31, 23, 59);
        Coupon coupon = Coupon.create("10000원 할인 쿠폰", BigDecimal.valueOf(10000), validStartDate, validEndDate, 30);
        Coupon savedCoupon = couponService.createCoupon(coupon);

        int threads = 40;
        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        CountDownLatch latch = new CountDownLatch(threads);

        // when
        for(int i = 1; i <= threads; i++) {
            long userId = i;
            executorService.submit(() -> {
                try {
                    couponService.requestCouponIssue(new CouponCommand.Issue(userId, savedCoupon.getId()));
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executorService.shutdown();

        for (int i = 0; i < threads / 10; i++) {
            couponWaitingQueue.couponIssue(); // 쿠폰 발급 배치 실행
        }

        // then
        int issuedCoupon = issuedCouponRepository.countByCouponId(coupon.getId());
        assertEquals(30, issuedCoupon);
    }

}