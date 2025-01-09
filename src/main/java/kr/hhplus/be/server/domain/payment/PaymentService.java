package kr.hhplus.be.server.domain.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public Payment payment(PaymentCommand.Payment paymentCommand) {
        Payment payment = Payment.create(paymentCommand);
        return paymentRepository.save(payment);
    }
}
