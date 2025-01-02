package kr.hhplus.be.server.interfaces.api.payment;

import kr.hhplus.be.server.interfaces.api.payment.dto.CreatePaymentRequest;
import kr.hhplus.be.server.interfaces.api.payment.dto.PaymentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody CreatePaymentRequest request) {
        PaymentResponse response = new PaymentResponse();
        return ResponseEntity.ok(response);
    }

}
