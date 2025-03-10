package kr.hhplus.be.server.interfaces.api.point;

import kr.hhplus.be.server.domain.point.PointInfo;
import lombok.Builder;

import java.math.BigDecimal;

public class PointResponse {
    public static Point of(PointInfo.Point point) {
        return Point.builder()
                .userId(point.userId())
                .point(point.point())
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
