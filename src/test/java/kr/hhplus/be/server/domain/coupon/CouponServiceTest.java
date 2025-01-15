package kr.hhplus.be.server.domain.coupon;

import kr.hhplus.be.server.support.exception.CustomException;
import kr.hhplus.be.server.support.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

    @Mock
    private IssuedCouponRepository issuedCouponRepository;

    @Mock
    private CouponRepository couponRepository;

    @InjectMocks
    private CouponService couponService;

    LocalDateTime validStartDate, validEndDate;
    @BeforeEach
    void setUp() {
        validStartDate = LocalDateTime.of(2025, 1, 1, 0, 0);
        validEndDate = LocalDateTime.of(2025, 1, 31, 23, 59);
    }

    @Test
    void 쿠폰_발급_요청_시_유저에게_해당_쿠폰이_있으면_예외가_발생한다() {
        // given
        CouponCommand.Issue command = CouponCommand.Issue.builder().userId(1L).couponId(1L).build();
        IssuedCoupon issuedCoupon = IssuedCoupon.create(1L, 1L, IssuedCouponStatus.UNUSED);

        given(issuedCouponRepository.findCouponByUserIdAndCouponId(1L, 1L)).willReturn(issuedCoupon);

        // when // then
        assertThatThrownBy(() -> couponService.issue(command))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.DUPLICATE_ISSUE_COUPON.getMessage());
    }

    @Test
    void 쿠폰_발급_요청_시_선착순_재고_없으면_예외가_발생한다() {
        // given
        CouponCommand.Issue command = CouponCommand.Issue.builder().userId(1L).couponId(1L).build();
        Coupon coupon = Coupon.create(1L, "5000원 할인 쿠폰", 5000, validStartDate, validEndDate, 0);

        given(couponRepository.findByIdWithLock(1L)).willReturn(coupon);

        // when // then
        assertThatThrownBy(() -> couponService.issue(command))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.SOLD_OUT_COUPON.getMessage());
    }

    @Test
    void 쿠폰_발급_요청_시_유저에게_쿠폰을_발급한다() {
        // given
        CouponCommand.Issue command = CouponCommand.Issue.builder().userId(1L).couponId(1L).build();
        Coupon coupon = Coupon.create(1L, "5000원 할인 쿠폰", 5000, validStartDate, validEndDate, 10);
        IssuedCoupon issuedCoupon = IssuedCoupon.create(1L, 1L, IssuedCouponStatus.UNUSED);

        given(couponRepository.findByIdWithLock(1L)).willReturn(coupon);
        given(issuedCouponRepository.save(any(IssuedCoupon.class))).willReturn(issuedCoupon);

        // when
        CouponInfo.IssuedCoupon result = couponService.issue(command);

        // then
        assertEquals(9, coupon.getQuantity());
        assertThat(result)
                .extracting("userId", "couponId", "status")
                .contains(1L, 1L, "UNUSED");
    }

    @Test
    void 쿠폰_사용_요청_시_유저에게_사용_가능한_쿠폰이_없으면_예외가_발생한다() {
        // given
        CouponCommand.Issue command = CouponCommand.Issue.builder().userId(1L).couponId(1L).build();

        given(issuedCouponRepository.findByUserIdAndCouponIdAndStatus(1L, 1L, IssuedCouponStatus.UNUSED)).willReturn(null);

        // when // then
        assertThatThrownBy(() -> couponService.use(command))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.NO_AVAILABLE_COUPON.getMessage());
    }

    @Test
    void 쿠폰_사용_요청_시_유저에게_사용_가능한_쿠폰이_있으면_쿠폰을_사용한다() {
        // given
        CouponCommand.Issue command = CouponCommand.Issue.builder().userId(1L).couponId(1L).build();

        Coupon coupon = Coupon.create(1L, "5000원 할인 쿠폰", 5000, validStartDate, validEndDate, 10);
        IssuedCoupon userCoupon = IssuedCoupon.create(1L, 1L, IssuedCouponStatus.UNUSED);

        given(issuedCouponRepository.findByUserIdAndCouponIdAndStatus(1L, 1L, IssuedCouponStatus.UNUSED)).willReturn(userCoupon);
        given(couponRepository.findByIdWithLock(coupon.getId())).willReturn(coupon);

        // when
        CouponInfo.IssuedCoupon result = couponService.use(command);

        // then
        assertEquals("USED", result.status());
    }

}