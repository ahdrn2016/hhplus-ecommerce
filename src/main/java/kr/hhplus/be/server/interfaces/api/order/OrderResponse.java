package kr.hhplus.be.server.interfaces.api.order;

public class OrderResponse {

    public record Order(Long orderId, int totalAmount, int discountAmount, int paymentAmount) {}
}
