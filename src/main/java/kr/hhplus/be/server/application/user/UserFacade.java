package kr.hhplus.be.server.application.user;

import kr.hhplus.be.server.domain.user.BalanceHistoryType;
import kr.hhplus.be.server.domain.user.UserService;
import kr.hhplus.be.server.interfaces.api.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;

    public UserResponse.Balance chargeBalance(UserParam.ChargeBalance param) {
        Long userId = param.userId();
        int amount = param.amount();

        userService.chargeBalance(userId, amount);
        userService.setBalanceHistory(userId, BalanceHistoryType.CHARGE, amount);
        return null;
    }
}
