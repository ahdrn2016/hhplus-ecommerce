package kr.hhplus.be.server.domain.user;

import lombok.Builder;

public class UserInfo {

    public static UserDto of(User user) {
        return UserDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .balance(user.getBalance())
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
