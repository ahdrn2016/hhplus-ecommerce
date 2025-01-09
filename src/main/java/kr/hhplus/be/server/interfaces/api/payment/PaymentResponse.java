package kr.hhplus.be.server.interfaces.api.payment;

public class PaymentResponse {

    public record Payment(Long paymentId, int totalAmount, int discountAmount, int paymentAmount) {}

}
