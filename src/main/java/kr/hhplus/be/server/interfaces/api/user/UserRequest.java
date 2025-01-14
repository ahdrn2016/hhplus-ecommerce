package kr.hhplus.be.server.interfaces.api.user;

import kr.hhplus.be.server.application.user.UserCriteria;

public class UserRequest {

    public record ChargeBalance(
            Long userId,
            int amount
    ) {
        public UserCriteria.ChargeBalance toCriteria() {
            return UserCriteria.ChargeBalance.builder()
                    .userId(userId)
                    .amount(amount)
                    .build();
        }
    }

}
