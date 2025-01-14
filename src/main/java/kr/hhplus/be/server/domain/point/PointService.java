package kr.hhplus.be.server.domain.point;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;
    private final PointHistoryRepository pointHistoryRepository;

    public PointInfo.Point point(Long userId) {
        Point point = pointRepository.findByUserId(userId);
        return PointInfo.of(point);
    }

    public PointInfo.Point charge(PointCommand.Charge command) {
        Point point = pointRepository.findByUserId(command.userId());
        point.add(command.amount());
        createPointHistory(point.getId(), command.amount(), PointHistoryType.CHARGE);
        return PointInfo.of(point);
    }

    public PointInfo.Point use(PointCommand.Use command) {
        Point point = pointRepository.findByUserId(command.userId());
        point.minus(command.amount());
        createPointHistory(point.getId(), command.amount(), PointHistoryType.USE);
        return PointInfo.of(point);
    }

    private void createPointHistory(Long pointId, int amount, PointHistoryType type) {
        PointHistory pointHistory = PointHistory.create(pointId, amount, type);
        pointHistoryRepository.save(pointHistory);
    }
}
