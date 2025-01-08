package kr.hhplus.be.server.interfaces.api.user;

import lombok.Builder;

public class UserResponse {

    public record Balance(Long userId, int balance) {
        @Builder
        public Balance {}
    }

}
