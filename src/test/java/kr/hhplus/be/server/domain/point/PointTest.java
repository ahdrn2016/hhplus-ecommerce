package kr.hhplus.be.server.domain.point;

import kr.hhplus.be.server.support.exception.CustomException;
import kr.hhplus.be.server.support.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PointTest {

    @Test
    void 잔액_충전_시_요청금액이_0원이면_실패한다() {
        // given
        int amount = 0;
        Point point = new Point(1L, 1L, 50000);

        // when // then
        assertThatThrownBy(() -> point.add(amount))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.INVALID_CHARGE_AMOUNT.getMessage());
    }

    @Test
    void 잔액_충전_요청_시_요청금액이_정상이면_보유잔액에_합산한다() {
        // given
        int amount = 10000;
        Point point = new Point(1L, 1L, 50000);

        // when
        point.add(amount);

        // then
        assertEquals(60000, point.getPoint());
    }

    @Test
    void 잔액_사용_시_요청금액이_보유잔액보다_크면_예외가_발생한다() {
        // given
        int amount = 80000;
        Point point = new Point(1L, 1L, 50000);

        // when // then
        assertThatThrownBy(() -> point.minus(amount))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.INSUFFICIENT_POINT.getMessage());
    }

    @Test
    void 잔액_사용_요청_시_요청금액이_정상이면_보유잔액에서_차감한다() {
        // given
        int amount = 10000;
        Point point = new Point(1L, 1L, 50000);

        // when
        point.minus(amount);

        // then
        assertEquals(40000, point.getPoint());
    }

}