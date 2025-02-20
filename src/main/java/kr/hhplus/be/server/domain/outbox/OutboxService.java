package kr.hhplus.be.server.domain.outbox;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OutboxService {

    private final OutboxRepository outboxRepository;

    @Transactional
    public Outbox create(Outbox outbox) {
        return outboxRepository.save(outbox);
    }

}
