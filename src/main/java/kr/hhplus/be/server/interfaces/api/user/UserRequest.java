package kr.hhplus.be.server.interfaces.api.user;

import kr.hhplus.be.server.application.user.UserParam;

public class UserRequest {

    public record ChargeBalance(
            Long userId,
            int amount
    ) {
        public UserParam.ChargeBalance toParam() {
            return UserParam.ChargeBalance.builder()
                    .userId(userId)
                    .amount(amount)
                    .build();
        }
    }

}
