package kr.hhplus.be.server.application.user;

import lombok.Builder;

public class UserParam {

    public record ChargeBalance(
            Long userId,
            int amount
    ) {
        @Builder
        public ChargeBalance {}
    }

}
