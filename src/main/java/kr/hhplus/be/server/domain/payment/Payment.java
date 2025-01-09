package kr.hhplus.be.server.domain.payment;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import kr.hhplus.be.server.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private int totalAmount;
    private int discountAmount;
    private int paymentAmount;

    @Builder
    public Payment(Long id, Long orderId, int totalAmount, int discountAmount, int paymentAmount) {
        this.id = id;
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.discountAmount = discountAmount;
        this.paymentAmount = paymentAmount;
    }

    public static Payment create(PaymentCommand.Payment payment) {
        return Payment.builder()
                .orderId(payment.orderId())
                .totalAmount(payment.totalAmount())
                .discountAmount(payment.discountAmount())
                .paymentAmount(payment.paymentAmount())
                .build();
    }
}
