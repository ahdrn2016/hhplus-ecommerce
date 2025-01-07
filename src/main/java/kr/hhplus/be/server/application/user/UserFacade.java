package kr.hhplus.be.server.application.user;

import kr.hhplus.be.server.domain.balance.BalanceHistoryService;
import kr.hhplus.be.server.domain.user.UserService;
import kr.hhplus.be.server.interfaces.api.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;
    private final BalanceHistoryService balanceHistoryService;

    public UserResponse.Balance chargeBalance(UserParam.ChargeBalance param) {
        UserResult.Balance user = userService.chargeBalance(param.toCommand());
        balanceHistoryService.saveHistory(param);
        return UserResult.toResponse(user);
    }
}
