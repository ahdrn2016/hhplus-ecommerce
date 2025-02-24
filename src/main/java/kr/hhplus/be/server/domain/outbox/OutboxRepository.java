package kr.hhplus.be.server.domain.outbox;

import java.util.List;

public interface OutboxRepository {

    Outbox save(Outbox outbox);

    Outbox findById(String messageId);

    List<Outbox> findAllByStatus(OutboxStatus status);

}
