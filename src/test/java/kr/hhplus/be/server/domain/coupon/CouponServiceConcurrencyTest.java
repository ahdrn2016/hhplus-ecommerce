package kr.hhplus.be.server.domain.coupon;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    @Test
    void 쿠폰_수량_5개일_때_유저_10명이_동시에_쿠폰_발급_요청_시_5명만_성공한다() throws InterruptedException {
        // given
        LocalDateTime validStartDate = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime validEndDate = LocalDateTime.of(2025, 1, 31, 23, 59);
        Coupon coupon = Coupon.create("5000원 할인 쿠폰", 5000, validStartDate, validEndDate, 5);
        Coupon savedCoupon = couponRepository.save(coupon);

        int threads = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        CountDownLatch latch = new CountDownLatch(threads);

        // when
        for(int i = 1; i <= threads; i++) {
            long userId = i;
            executorService.submit(() -> {
                try {
                    CouponCommand.Issue command = CouponCommand.Issue.builder().userId(userId).couponId(coupon.getId()).build();
                    couponService.issue(command);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executorService.shutdown();

        //then
        Coupon result = couponRepository.findById(savedCoupon.getId());
        assertEquals(0, result.getQuantity());
    }

}