package kr.hhplus.be.server.application.user;

import kr.hhplus.be.server.domain.user.UserCommand;
import lombok.Builder;

public class UserParam {

    public record ChargeBalance(Long userId, int amount) {

        public UserCommand.ChargeBalance toCommand() {
            return UserCommand.ChargeBalance.builder()
                    .userId(userId)
                    .amount(amount)
                    .build();
        }

        @Builder
        public ChargeBalance {}
    }

}
