package kr.hhplus.be.server.interfaces.api.user;

import kr.hhplus.be.server.application.user.UserResult;
import kr.hhplus.be.server.domain.user.UserInfo;
import lombok.Builder;

public class UserResponse {

    public static UserDto of(UserInfo.UserDto userDto) {
        return UserDto.builder()
                .userId(userDto.userId())
                .name(userDto.name())
                .balance(userDto.balance())
                .build();
    }

    public static UserDto of(UserResult.UserDto userDto) {
        return UserDto.builder()
                .userId(userDto.userId())
                .name(userDto.name())
                .balance(userDto.balance())
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
