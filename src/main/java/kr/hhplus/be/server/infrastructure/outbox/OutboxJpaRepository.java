package kr.hhplus.be.server.infrastructure.outbox;

import kr.hhplus.be.server.domain.outbox.Outbox;
import kr.hhplus.be.server.domain.outbox.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboxJpaRepository extends JpaRepository<Outbox, String> {

    List<Outbox> findAllByStatus(OutboxStatus status);

}
