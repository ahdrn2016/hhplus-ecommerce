package kr.hhplus.be.server.interfaces.api.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderResponse {

    private Long orderId;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private BigDecimal paymentAmount;
    private LocalDateTime createdAt;

}
