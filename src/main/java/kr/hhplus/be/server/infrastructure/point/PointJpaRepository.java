package kr.hhplus.be.server.infrastructure.point;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.domain.point.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PointJpaRepository extends JpaRepository<Point, Long> {

    Point findByUserId(Long userId);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT p FROM Point p WHERE p.userId = :userId")
    Point findByUserIdWithLock(@Param("userId") Long userId);

}
