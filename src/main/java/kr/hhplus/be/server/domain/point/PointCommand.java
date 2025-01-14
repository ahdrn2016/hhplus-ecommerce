package kr.hhplus.be.server.domain.point;

import lombok.Builder;

public class PointCommand {
    public record Charge(
            Long userId,
            int amount
    ) {
        @Builder
        public Charge {}
    }

    public record Use(
            Long userId,
            int amount
    ) {
        @Builder
        public Use {}
    }
}
