package kr.hhplus.be.server.domain.user;

import kr.hhplus.be.server.application.user.UserResult;
import kr.hhplus.be.server.interfaces.api.user.UserResponse;

public class UserInfo {

    public static UserResponse.Balance toBalanceResponse(User user) {
        return UserResponse.Balance.builder()
                .userId(user.getId())
                .balance(user.getBalance())
                .build();
    }

    public static UserResult.Balance toBalanceResult(User user) {
        return UserResult.Balance.builder()
                .userId(user.getId())
                .balance(user.getBalance())
                .build();
    }

}
