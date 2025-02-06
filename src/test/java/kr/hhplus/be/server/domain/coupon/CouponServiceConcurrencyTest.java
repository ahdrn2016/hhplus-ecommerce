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
    private IssuedCouponRepository issuedCouponRepository;

    @Test
    void 쿠폰_수량_5개일_때_유저_10명이_동시에_쿠폰_발급_요청_시_5명만_성공한다() throws InterruptedException {
        // given
        LocalDateTime validStartDate = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime validEndDate = LocalDateTime.of(2025, 12, 31, 23, 59);
        Coupon coupon = Coupon.create("10000원 할인 쿠폰", BigDecimal.valueOf(10000), validStartDate, validEndDate, 5);
        Coupon savedCoupon = couponService.createCoupon(coupon);

        int threads = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        CountDownLatch latch = new CountDownLatch(threads);

        // when
        for(int i = 1; i <= 10; i++) {
            long userId = i;
            executorService.submit(() -> {
                try {
                    couponService.couponIssue(new CouponCommand.Issue(userId, savedCoupon.getId()));
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executorService.shutdown();

        couponService.issue(); // 쿠폰 발급 배치 실행

        // then
        int issuedCoupon = issuedCouponRepository.countByCouponId(coupon.getId());
        assertEquals(5, issuedCoupon);
    }

}