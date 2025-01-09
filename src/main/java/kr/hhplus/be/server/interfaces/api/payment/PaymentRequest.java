package kr.hhplus.be.server.interfaces.api.payment;

import kr.hhplus.be.server.application.payment.PaymentParam;

public class PaymentRequest {

    public record Payment(Long orderId) {
        public PaymentParam.Payment toParam() {
            return new PaymentParam.Payment(orderId);
        }
    }

}
