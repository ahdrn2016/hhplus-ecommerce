package kr.hhplus.be.server.application.user;

import kr.hhplus.be.server.interfaces.api.user.UserResponse;
import lombok.Builder;

public class UserResult {

    public record Balance(Long userId, int balance) {
        @Builder
        public Balance {}
    }

    public static UserResponse.Balance toResponse(Balance user) {
        return UserResponse.Balance.builder()
                .userId(user.userId)
                .balance(user.balance)
                .build();
    }

}
