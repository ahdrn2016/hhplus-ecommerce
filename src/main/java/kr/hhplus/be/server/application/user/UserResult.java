package kr.hhplus.be.server.application.user;

import kr.hhplus.be.server.domain.user.UserInfo;
import lombok.Builder;

public class UserResult {

    public record UserDto(Long userId, String name, int balance) {
        @Builder
        public UserDto {}
    }
    public static UserDto of(UserInfo.UserDto userInfo) {
        return UserDto.builder()
                .userId(userInfo.userId())
                .name(userInfo.name())
                .balance(userInfo.balance())
                .build();
    }

}
