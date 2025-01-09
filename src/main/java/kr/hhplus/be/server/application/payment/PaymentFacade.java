package kr.hhplus.be.server.application.payment;

import kr.hhplus.be.server.domain.order.OrderService;
import kr.hhplus.be.server.domain.payment.PaymentService;
import kr.hhplus.be.server.domain.user.UserService;
import kr.hhplus.be.server.interfaces.api.payment.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentFacade {

    private final UserService userService;
    private final OrderService orderService;
    private final PaymentService paymentService;

    public PaymentResponse.Payment createPayment(PaymentParam.Payment param) {

        PaymentResult.Order order = orderService.getOrder(param.orderId());

        // 유저 잔액 차감 후 잔액 사용 기록
        userService.useBalance(order.userId(), order.paymentAmount());

        // 결제 저장
        paymentService.payment(order.toCommand());

        return null;
    }
}
