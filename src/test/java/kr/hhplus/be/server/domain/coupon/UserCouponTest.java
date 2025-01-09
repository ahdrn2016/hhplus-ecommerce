package kr.hhplus.be.server.domain.coupon;

import kr.hhplus.be.server.support.exception.CustomException;
import kr.hhplus.be.server.support.exception.ErrorCode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class UserCouponTest {

    @Test
    void 이미_사용한_쿠폰을_사용하면_예외가_발생한다() {
        // given
        Coupon coupon = new Coupon();
        UserCoupon userCoupon = UserCoupon.builder()
                .coupon(coupon)
                .status(UserCouponStatus.USED)
                .build();

        // when // then
        assertThatThrownBy(() -> userCoupon.used())
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.ALREADY_USED_COUPON.getMessage());
    }

    @Test
    void 사용_안한_쿠폰을_사용하면_USED_상태가_된다() {
        // given
        Coupon coupon = new Coupon();
        UserCoupon userCoupon = UserCoupon.builder()
                .coupon(coupon)
                .status(UserCouponStatus.UNUSED)
                .build();

        // when
        userCoupon.used();

        // then
        assertThat(userCoupon.getStatus()).isEqualTo(UserCouponStatus.USED);
    }

}