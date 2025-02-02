package kr.hhplus.be.server.domain.point;

import lombok.Builder;

import java.math.BigDecimal;

public class PointCommand {
    public record Charge(
            Long userId,
            BigDecimal amount
    ) {
        @Builder
        public Charge {}
    }

    public record Use(
            Long userId,
            BigDecimal amount
    ) {
        @Builder
        public Use {}
    }
}
