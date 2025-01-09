package kr.hhplus.be.server.interfaces.api.payment;

import io.swagger.v3.oas.annotations.Operation;
import kr.hhplus.be.server.application.payment.PaymentFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentFacade paymentFacade;

    @PostMapping(produces = { "application/json" }, consumes = { "application/json" })
    @Operation(summary = "결제", description = "주문을 결제합니다.", tags = { "payment" })
    public ResponseEntity<PaymentResponse.Payment> createPayment(@RequestBody PaymentRequest.Payment request) {
        PaymentResponse.Payment response = paymentFacade.createPayment(request.toParam());
        return ResponseEntity.ok(response);
    }

}
