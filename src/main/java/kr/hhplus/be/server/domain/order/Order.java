package kr.hhplus.be.server.domain.order;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private int totalAmount;

    private int discountAmount;

    private int paymentAmount;

    @Builder
    public Order(Long userId, int totalAmount, int discountAmount) {
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.discountAmount = discountAmount;
        this.paymentAmount = totalAmount - discountAmount;
    }

    public static Order create(Long userId, int discountAmount, int totalAmount) {
        return Order.builder()
                .userId(userId)
                .totalAmount(totalAmount)
                .discountAmount(discountAmount)
                .build();
    }
}
