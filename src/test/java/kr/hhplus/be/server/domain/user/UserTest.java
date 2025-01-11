package kr.hhplus.be.server.domain.user;

import kr.hhplus.be.server.support.exception.CustomException;
import kr.hhplus.be.server.support.exception.ErrorCode;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void 잔액_충전_시_요청금액이_0원이면_실패한다() {
        // given
        User user = User.create(1L, "유혜원",0);
        int amount = 0;

        // when // then
        assertThatThrownBy(() -> user.addBalance(amount))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.INVALID_CHARGE_AMOUNT.getMessage());
    }

    @Test
    public void 잔액_충전_시_요청금액이_정상이면_보유잔액에_합산한다() {
        // given
        User user = User.create(1L, "유혜원", 10000);

        // when
        user.addBalance(5000);

        // then
        assertEquals(15000, user.getBalance());
    }

    @Test
    public void 잔액_사용_시_요청금액이_크면_예외가_발생한다() {
        // given
        User user = User.create(1L, "유혜원", 10000);

        // when // then
        assertThatThrownBy(() -> user.minusBalance(20000))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.INSUFFICIENT_BALANCE.getMessage());
    }

    @Test
    public void 잔액_사용_시_요청금액이_정상이면_보유잔액에서_차감한다() {
        // given
        User user = User.create(1L, "유혜원", 10000);

        // when
        user.minusBalance(5000);

        // then
        assertEquals(5000, user.getBalance());
    }
}