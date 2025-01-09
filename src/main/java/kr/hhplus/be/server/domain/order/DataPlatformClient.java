package kr.hhplus.be.server.domain.order;

import org.springframework.stereotype.Component;

@Component
public class DataPlatformClient {

    public boolean send(Order savedOrder) {
        return true;
    }
}
