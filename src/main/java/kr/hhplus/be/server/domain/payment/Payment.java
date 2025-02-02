package kr.hhplus.be.server.domain.payment;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import kr.hhplus.be.server.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private BigDecimal totalAmount;

    private BigDecimal discountAmount;

    private BigDecimal paymentAmount;

    @Builder
    public Payment(Long id, Long orderId, BigDecimal totalAmount, BigDecimal discountAmount) {
        this.id = id;
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.discountAmount = discountAmount;
        this.paymentAmount = totalAmount.subtract(discountAmount);
    }

    public static Payment create(Long orderId, BigDecimal totalAmount, BigDecimal discountAmount) {
        return Payment.builder()
                .orderId(orderId)
                .totalAmount(totalAmount)
                .discountAmount(discountAmount)
                .build();
    }
}
