package kr.hhplus.be.server.domain.point;

import kr.hhplus.be.server.support.exception.CustomException;
import kr.hhplus.be.server.support.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class PointServiceTest {

    @Mock
    private PointRepository pointRepository;

    @Mock
    private PointHistoryRepository pointHistoryRepository;

    @InjectMocks
    private PointService pointService;

    @Test
    void 잔액_충전_시_요청금액이_0원이면_예외가_발생한다() {
        // given
        PointCommand.Charge command = PointCommand.Charge.builder().userId(1L).amount(0).build();
        Point point = new Point(1L, 1L, 50000);

        given(pointRepository.findByUserIdWithLock(1L)).willReturn(point);

        // when // then
        assertThatThrownBy(() -> pointService.charge(command))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.INVALID_CHARGE_AMOUNT.getMessage());
    }

    @Test
    void 잔액_충전_요청_시_요청금액이_정상이면_보유잔액에_합산한다() {
        // given
        PointCommand.Charge command = PointCommand.Charge.builder().userId(1L).amount(10000).build();
        Point point = new Point(1L, 1L, 50000);
        PointHistory pointHistory = PointHistory.create(1L, 10000, PointHistoryType.CHARGE);

        given(pointRepository.findByUserIdWithLock(1L)).willReturn(point);
        given(pointHistoryRepository.save(any(PointHistory.class))).willReturn(pointHistory);

        // when
        PointInfo.Point result = pointService.charge(command);

        // then
        assertEquals(60000, result.point());
    }

    @Test
    void 잔액_사용_시_요청금액이_보유잔액보다_크면_예외가_발생한다() {
        // given
        PointCommand.Use command = PointCommand.Use.builder().userId(1L).amount(60000).build();
        Point point = new Point(1L, 1L, 50000);

        given(pointRepository.findByUserIdWithLock(1L)).willReturn(point);

        // when // then
        assertThatThrownBy(() -> pointService.use(command))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.INSUFFICIENT_POINT.getMessage());
    }

    @Test
    void 잔액_사용_요청_시_요청금액이_정상이면_보유잔액에서_차감한다() {
        // given
        PointCommand.Use command = PointCommand.Use.builder().userId(1L).amount(10000).build();
        Point point = new Point(1L, 1L, 50000);
        PointHistory pointHistory = PointHistory.create(1L, 10000, PointHistoryType.USE);

        given(pointRepository.findByUserIdWithLock(1L)).willReturn(point);
        given(pointHistoryRepository.save(any(PointHistory.class))).willReturn(pointHistory);

        // when
        PointInfo.Point result = pointService.use(command);

        // then
        assertEquals(40000, result.point());
    }
}