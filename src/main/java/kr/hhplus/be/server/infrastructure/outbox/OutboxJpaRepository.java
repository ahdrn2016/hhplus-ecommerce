package kr.hhplus.be.server.infrastructure.outbox;

import kr.hhplus.be.server.domain.outbox.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxJpaRepository extends JpaRepository<Outbox, String> {
}
