package kr.hhplus.be.server.interfaces.api.payment.dto;

import java.math.BigDecimal;

public class PaymentResponse {

    private Long paymentId;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private BigDecimal paymentAmount;

}
