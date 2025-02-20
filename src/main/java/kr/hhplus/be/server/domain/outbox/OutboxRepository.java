package kr.hhplus.be.server.domain.outbox;

public interface OutboxRepository {

    Outbox save(Outbox outbox);

}
