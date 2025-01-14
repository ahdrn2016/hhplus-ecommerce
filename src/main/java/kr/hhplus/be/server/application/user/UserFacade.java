package kr.hhplus.be.server.application.user;

import kr.hhplus.be.server.domain.user.BalanceHistoryType;
import kr.hhplus.be.server.domain.user.UserInfo;
import kr.hhplus.be.server.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;

    @Transactional
    public UserResult.UserDto chargeBalance(UserCriteria.ChargeBalance criteria) {
        Long userId = criteria.userId();
        int amount = criteria.amount();

        // 잔액 충전
        UserInfo.UserDto user = userService.chargeBalance(userId, amount);
        // 잔액 충전 기록
        userService.setBalanceHistory(userId, BalanceHistoryType.CHARGE, amount);

        return UserResult.of(user);
    }
}
