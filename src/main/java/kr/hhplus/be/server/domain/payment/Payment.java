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

    private int paymentAmount;

    @Builder
    public Payment(Long id, Long orderId, int paymentAmount) {
        this.id = id;
        this.orderId = orderId;
        this.paymentAmount = paymentAmount;
    }

    public static Payment create(Long orderId, int paymentAmount) {
        return Payment.builder()
                .orderId(orderId)
                .paymentAmount(paymentAmount)
                .build();
    }
}
