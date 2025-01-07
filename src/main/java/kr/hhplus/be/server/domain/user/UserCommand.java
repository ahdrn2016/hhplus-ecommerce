package kr.hhplus.be.server.domain.user;


import lombok.Builder;

public class UserCommand {

    public record ChargeBalance(Long userId, int amount) {
        @Builder
        public ChargeBalance {}
    }

}
