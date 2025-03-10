package kr.hhplus.be.server.domain.point;

public interface PointRepository {

    Point findByUserId(Long userId);

    Point findByUserIdWithLock(Long userId);
}
