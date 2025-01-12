package kr.hhplus.be.server.domain.user;

import kr.hhplus.be.server.interfaces.api.user.UserResponse;
import kr.hhplus.be.server.support.exception.CustomException;
import kr.hhplus.be.server.support.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void 잔액_충전_시_요청금액이_0원이면_실패한다() {
        // given
        User user = User.create(1L, "유혜원", 0);
        int amount = 0;

        // when // then
        assertThatThrownBy(() -> user.addBalance(amount))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.INVALID_CHARGE_AMOUNT.getMessage());
    }

    @Test
    void 잔액_충전_요청_시_요청금액이_정상이면_보유잔액에_합산한다() {
        // given
        Long userId = 1L;
        int balance = 50000;
        int amount = 10000;
        int totalAmount = balance + amount;

        User user = User.create(userId, "유혜원", balance);

        given(userRepository.findById(userId)).willReturn(user);

        // when
        UserResponse.UserDto result = userService.chargeBalance(userId, amount);

        // then
        assertEquals(totalAmount, result.balance());
    }

    @Test
    void 잔액_사용_시_요청금액이_보유잔액보다_크면_예외가_발생한다() {
        // given
        User user = User.create(1L, "유혜원", 10000);

        // when // then
        assertThatThrownBy(() -> user.minusBalance(20000))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.INSUFFICIENT_BALANCE.getMessage());
    }

    @Test
    void 잔액_사용_요청_시_요청금액이_정상이면_보유잔액에서_차감한다() {
        // given
        Long userId = 1L;
        int balance = 50000;
        int amount = 10000;
        int totalAmount = balance - amount;

        User user = User.create(userId, "유혜원", balance);

        given(userRepository.findById(userId)).willReturn(user);

        // when
        UserResponse.UserDto result = userService.useBalance(userId, amount);

        // then
        assertEquals(totalAmount, result.balance());
        assertEquals(totalAmount, user.getBalance());
    }

}