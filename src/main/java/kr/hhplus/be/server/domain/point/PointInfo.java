package kr.hhplus.be.server.domain.point;

import lombok.Builder;

public class PointInfo {
    public static Point of(kr.hhplus.be.server.domain.point.Point point) {
        return Point.builder()
                .userId(point.getUserId())
                .point(point.getPoint())
                .build();
    }

    public record Point(
            Long userId,
            int point
    ) {
        @Builder
        public Point {}
    }
}

