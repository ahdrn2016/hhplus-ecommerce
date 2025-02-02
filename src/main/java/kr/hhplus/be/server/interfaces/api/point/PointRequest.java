package kr.hhplus.be.server.interfaces.api.point;

import kr.hhplus.be.server.domain.point.PointCommand;

import java.math.BigDecimal;

public class PointRequest {
    public record Charge(
            Long userId,
            BigDecimal amount
    ) {
        public PointCommand.Charge toCommand() {
            return PointCommand.Charge.builder()
                    .userId(userId)
                    .amount(amount)
                    .build();
        }
    }
}
