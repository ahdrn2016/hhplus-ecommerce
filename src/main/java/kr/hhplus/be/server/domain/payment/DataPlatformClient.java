package kr.hhplus.be.server.domain.payment;

import org.springframework.stereotype.Component;

@Component
public class DataPlatformClient {

    public boolean sendData(Payment payment) {
        return true;
    }

}
