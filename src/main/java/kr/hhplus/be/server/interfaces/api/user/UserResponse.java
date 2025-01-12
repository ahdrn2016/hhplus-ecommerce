package kr.hhplus.be.server.interfaces.api.user;

import lombok.Builder;

public class UserResponse {

    public record UserDto(
            Long userId,
            String name,
            int balance
    ) {
        @Builder
        public UserDto {}
    }

}
