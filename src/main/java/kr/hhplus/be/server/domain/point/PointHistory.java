package kr.hhplus.be.server.domain.point;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PointHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long pointId;

    private int point;

    @Enumerated(EnumType.STRING)
    private PointHistoryType type;

    @Builder
    public PointHistory(Long pointId, int point, PointHistoryType type) {
        this.pointId = pointId;
        this.point = point;
        this.type = type;
    }

    public static PointHistory create(Long pointId, int amount, PointHistoryType type) {
        return PointHistory.builder()
                .pointId(pointId)
                .point(amount)
                .type(type)
                .build();
    }
}
