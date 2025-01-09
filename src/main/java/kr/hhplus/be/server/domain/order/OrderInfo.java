package kr.hhplus.be.server.domain.order;

import kr.hhplus.be.server.application.order.OrderResult;

public class OrderInfo {

    public static OrderResult.Order toResult(Order savedOrder) {
        return OrderResult.Order.builder()
                .orderId(savedOrder.getId())
                .totalAmount(savedOrder.getTotalAmount())
                .discountAmount(savedOrder.getDiscountAmount())
                .paymentAmount(savedOrder.getPaymentAmount())
                .build();
    }

}
