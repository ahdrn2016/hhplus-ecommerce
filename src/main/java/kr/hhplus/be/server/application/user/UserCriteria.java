package kr.hhplus.be.server.application.user;

import lombok.Builder;

public class UserCriteria {

    public record ChargeBalance(
            Long userId,
            int amount
    ) {
        @Builder
        public ChargeBalance {}
    }

}
