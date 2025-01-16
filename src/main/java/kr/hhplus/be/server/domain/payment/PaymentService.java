package kr.hhplus.be.server.domain.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentInfo.Payment payment(Long orderId, int totalAmount, int discountAmount) {
        Payment payment = Payment.create(orderId, totalAmount, discountAmount);
        Payment savedPayment = paymentRepository.save(payment);
        return PaymentInfo.of(savedPayment);
    }
}
