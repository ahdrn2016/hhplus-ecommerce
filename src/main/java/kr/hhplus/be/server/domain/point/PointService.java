package kr.hhplus.be.server.domain.point;

import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;
    private final PointHistoryRepository pointHistoryRepository;

    public PointInfo.Point point(Long userId) {
        Point point = pointRepository.findByUserId(userId);
        return PointInfo.of(point);
    }

    @Retryable(
            retryFor = ObjectOptimisticLockingFailureException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 500)
    )
    @Transactional
    public PointInfo.Point charge(PointCommand.Charge command) {
        Point point = pointRepository.findByUserIdWithLock(command.userId());
        point.add(command.amount());
        createPointHistory(point.getId(), command.amount(), PointHistoryType.CHARGE);
        return PointInfo.of(point);
    }

    @Retryable(
            retryFor = ObjectOptimisticLockingFailureException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 500)
    )
    @Transactional
    public PointInfo.Point use(PointCommand.Use command) {
        Point point = pointRepository.findByUserIdWithLock(command.userId());
        point.minus(command.amount());
        createPointHistory(point.getId(), command.amount(), PointHistoryType.USE);
        return PointInfo.of(point);
    }

    private void createPointHistory(Long pointId, int amount, PointHistoryType type) {
        PointHistory pointHistory = PointHistory.create(pointId, amount, type);
        pointHistoryRepository.save(pointHistory);
    }
}
