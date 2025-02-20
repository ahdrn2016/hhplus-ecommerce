package kr.hhplus.be.server.domain.order;

import org.springframework.stereotype.Component;

@Component
public class DataPlatformClient {

    public boolean sendData(OrderEvent.Completed order) {
        return true;
    }

}
