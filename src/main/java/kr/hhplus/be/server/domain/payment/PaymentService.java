package kr.hhplus.be.server.domain.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final DataPlatformClient dataPlatformClient;

    public boolean payment(Long orderId, int paymentAmount, int discountAmount) {
        Payment payment = Payment.create(orderId, paymentAmount);
        Payment savedPayment = paymentRepository.save(payment);

        boolean response = dataPlatformClient.sendData(savedPayment);

        return response;
    }
}
