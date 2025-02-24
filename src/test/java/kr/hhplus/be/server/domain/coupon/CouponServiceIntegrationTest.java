package kr.hhplus.be.server.domain.coupon;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CouponServiceIntegrationTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponWaitingQueue couponWaitingQueue;

    @Autowired
    private IssuedCouponRepository issuedCouponRepository;

    @Test
    void 유저가_쿠폰_발급_요청_시_발급에_성공한다() {
        // given
        LocalDateTime validStartDate = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime validEndDate = LocalDateTime.of(2025, 12, 31, 23, 59);
        Coupon coupon = Coupon.create("10000원 할인 쿠폰", BigDecimal.valueOf(10000), validStartDate, validEndDate, 10);
        Coupon savedCoupon = couponService.createCoupon(coupon);

        // when
        couponService.issue(new CouponCommand.Issue(1L, savedCoupon.getId()));
        couponWaitingQueue.couponIssue();

        // then
        assertThat(issuedCouponRepository.findCouponByUserIdAndCouponId(1L, savedCoupon.getId()))
                .extracting("userId", "couponId", "status")
                .contains(1L, savedCoupon.getId(), IssuedCouponStatus.UNUSED);
    }

}