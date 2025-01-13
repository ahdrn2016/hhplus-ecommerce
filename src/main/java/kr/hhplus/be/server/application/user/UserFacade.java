package kr.hhplus.be.server.application.user;

import kr.hhplus.be.server.domain.user.BalanceHistoryType;
import kr.hhplus.be.server.domain.user.UserInfo;
import kr.hhplus.be.server.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;

    public UserResult.UserDto chargeBalance(UserParam.ChargeBalance param) {
        Long userId = param.userId();
        int amount = param.amount();

        // 잔액 충전
        UserInfo.UserDto user = userService.chargeBalance(userId, amount);
        // 잔액 충전 기록
        userService.setBalanceHistory(userId, BalanceHistoryType.CHARGE, amount);

        return UserResult.of(user);
    }
}
