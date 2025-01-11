package kr.hhplus.be.server.domain.user;

import kr.hhplus.be.server.interfaces.api.user.UserResponse;

public class UserInfo {

    public static UserResponse.Balance toResponse(
            User user
    ) {
        return UserResponse.Balance.builder()
                .userId(user.getId())
                .balance(user.getBalance())
                .build();
    }

}
