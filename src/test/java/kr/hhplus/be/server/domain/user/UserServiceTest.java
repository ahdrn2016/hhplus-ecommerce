package kr.hhplus.be.server.domain.user;

import kr.hhplus.be.server.interfaces.api.user.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void 잔액_충전_요청_시_보유잔액에_요청금액을_합산한다() {
        // given
        Long userId = 1L;
        int balance = 50000;
        int amount = 10000;
        int totalAmount = balance + amount;

        User user = User.builder()
                .id(userId)
                .name("유혜원")
                .balance(balance)
                .build();

        given(userRepository.findBalanceByUserId(userId)).willReturn(user);

        // when
        UserResponse.Balance result = userService.chargeBalance(userId, amount);

        // then
        assertEquals(totalAmount, result.balance());
        assertEquals(totalAmount, user.getBalance());
    }

    @Test
    void 잔액_사용_요청_시_보유잔액에_요청금액을_차감한다() {
        // given
        Long userId = 1L;
        int balance = 50000;
        int amount = 10000;
        int totalAmount = balance - amount;

        User user = User.builder()
                .id(userId)
                .name("유혜원")
                .balance(balance)
                .build();

        given(userRepository.findBalanceByUserId(userId)).willReturn(user);

        // when
        UserResponse.Balance result = userService.useBalance(userId, amount);

        // then
        assertEquals(totalAmount, result.balance());
        assertEquals(totalAmount, user.getBalance());
    }

}