package kr.hhplus.be.server.domain.point;

import lombok.Builder;

import java.math.BigDecimal;

public class PointInfo {
    public static Point of(kr.hhplus.be.server.domain.point.Point point) {
        return Point.builder()
                .userId(point.getUserId())
                .point(point.getPoint())
                .build();
    }

    public record Point(
            Long userId,
            BigDecimal point
    ) {
        @Builder
        public Point {}
    }
}

