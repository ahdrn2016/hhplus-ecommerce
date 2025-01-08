package kr.hhplus.be.server.interfaces.api.user;

import kr.hhplus.be.server.domain.user.UserCommand;

public class UserRequest {

    public record ChargeBalance(Long userId, int amount) {
        public UserCommand.ChargeBalance toCommand() {
            return UserCommand.ChargeBalance.builder()
                    .userId(userId)
                    .amount(amount)
                    .build();
        }
    }

}
