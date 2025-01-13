package kr.hhplus.be.server.interfaces.api.user;

import kr.hhplus.be.server.application.user.UserResult;
import kr.hhplus.be.server.domain.user.UserInfo;
import lombok.Builder;

public class UserResponse {

    public static UserDto of(UserInfo.UserDto userInfo) {
        return UserDto.builder()
                .userId(userInfo.userId())
                .name(userInfo.name())
                .balance(userInfo.balance())
                .build();
    }

    public static UserDto of(UserResult.UserDto userResult) {
        return UserDto.builder()
                .userId(userResult.userId())
                .name(userResult.name())
                .balance(userResult.balance())
                .build();
    }

    public record UserDto(
            Long userId,
            String name,
            int balance
    ) {
        @Builder
        public UserDto {}
    }

}
