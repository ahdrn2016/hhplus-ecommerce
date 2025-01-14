package kr.hhplus.be.server.interfaces.api.point;

import kr.hhplus.be.server.domain.point.PointCommand;

public class PointRequest {
    public record Point(
            Long userId,
            int amount
    ) {
        public PointCommand.Charge toCommand() {
            return PointCommand.Charge.builder()
                    .userId(userId)
                    .amount(amount)
                    .build();
        }
    }
}
