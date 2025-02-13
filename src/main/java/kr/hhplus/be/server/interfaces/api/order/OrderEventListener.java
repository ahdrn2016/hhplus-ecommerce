package kr.hhplus.be.server.interfaces.api.order;

import kr.hhplus.be.server.domain.order.DataPlatformClient;
import kr.hhplus.be.server.domain.order.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.event.TransactionPhase.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventListener {

    private final DataPlatformClient dataPlatformClient;

    @Async
    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void handleOrderEvent(OrderEvent.Complete order) {
        try {
            dataPlatformClient.sendData(order);
        } catch (Exception e) {
            log.error("send to data platform failed: {}", e.getMessage());
        }
    }

}
