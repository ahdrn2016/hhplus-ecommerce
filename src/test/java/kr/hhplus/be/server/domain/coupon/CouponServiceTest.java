package kr.hhplus.be.server.domain.coupon;

import kr.hhplus.be.server.interfaces.api.coupon.CouponResponse;
import kr.hhplus.be.server.support.exception.CustomException;
import kr.hhplus.be.server.support.exception.ErrorCode;
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
    private UserCouponRepository userCouponRepository;

    @Mock
    private CouponRepository couponRepository;

    @InjectMocks
    private CouponService couponService;

    @Test
    void 쿠폰_발급_요청_시_선착순_재고_없으면_예외가_발생한다() {
        // given
        Long couponId = 1L;
        LocalDateTime validStartDate = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime validEndDate = LocalDateTime.of(2025, 1, 31, 23, 59);
        Coupon coupon = Coupon.create(couponId, "5000원 할인 쿠폰", 5000, validStartDate, validEndDate, 0);

        given(couponRepository.findByIdWithLock(couponId)).willReturn(coupon);

        // when // then
        assertThatThrownBy(() -> couponService.issueCoupon(couponId))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.SOLD_OUT_COUPON.getMessage());
    }

    @Test
    void 쿠폰_발급_요청_시_선착순_재고_있으면_쿠폰_잔여_수량을_차감한다() {
        // given
        Long couponId = 1L;
        LocalDateTime validStartDate = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime validEndDate = LocalDateTime.of(2025, 1, 31, 23, 59);
        Coupon coupon = Coupon.create(couponId, "5000원 할인 쿠폰", 5000, validStartDate, validEndDate, 10);

        given(couponRepository.findByIdWithLock(couponId)).willReturn(coupon);

        // when
        CouponResponse.CouponDto result = couponService.issueCoupon(couponId);

        // then
        assertEquals(9, result.quantity());
    }

    @Test
    void 쿠폰_발급_요청_시_유저에게_해당_쿠폰이_있으면_예외가_발생한다() {
        // given
        Long userId = 1L;
        Long couponId = 1L;
        UserCoupon userCoupon = UserCoupon.create(userId, couponId, UserCouponStatus.UNUSED);

        given(userCouponRepository.findCouponByUserIdAndCouponId(userId, couponId)).willReturn(userCoupon);

        // when // then
        assertThatThrownBy(() -> couponService.createUserCoupon(userId, couponId))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.DUPLICATE_ISSUE_COUPON.getMessage());
    }

    @Test
    void 쿠폰_발급_요청_시_유저에게_해당_쿠폰이_없으면_쿠폰을_발급한다() {
        // given
        Long userId = 1L;
        Long couponId = 1L;

        UserCoupon userCoupon = UserCoupon.create(userId, couponId, UserCouponStatus.UNUSED);

        given(userCouponRepository.findCouponByUserIdAndCouponId(userId, couponId)).willReturn(null);
        given(userCouponRepository.save(any(UserCoupon.class))).willReturn(userCoupon);

        // when
        CouponResponse.UserCouponDto result = couponService.createUserCoupon(userId, couponId);

        // then
        assertThat(result)
                .extracting("userId", "couponId", "status")
                .contains(userId, couponId, "UNUSED");
    }

    @Test
    void 쿠폰_사용_요청_시_유저에게_사용_가능한_쿠폰이_없으면_예외가_발생한다() {
        // given
        Long userId = 1L;
        Long couponId = 1L;

        given(userCouponRepository.findByUserIdAndCouponIdAndStatus(userId, couponId, UserCouponStatus.UNUSED)).willReturn(null);

        // when // then
        assertThatThrownBy(() -> couponService.useCoupon(userId, couponId))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.NO_AVAILABLE_COUPON.getMessage());
    }

    @Test
    void 쿠폰_사용_요청_시_유저에게_사용_가능한_쿠폰이_있으면_쿠폰을_사용한다() {
        // given
        Long userId = 1L;
        Long couponId = 1L;
        UserCoupon userCoupon = UserCoupon.create(userId, couponId, UserCouponStatus.UNUSED);

        given(userCouponRepository.findByUserIdAndCouponIdAndStatus(userId, couponId, UserCouponStatus.UNUSED)).willReturn(userCoupon);

        // when
        CouponResponse.UserCouponDto result = couponService.useCoupon(userId, couponId);

        // then
        assertEquals("USED", result.status());
    }

}