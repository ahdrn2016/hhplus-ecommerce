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

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private int totalAmount;

    @Builder
    public Order(Long userId, int totalAmount) {
        this.userId = userId;
        this.totalAmount = totalAmount;
    }

    public static Order create(Long userId, int totalAmount) {
        return Order.builder()
                .userId(userId)
                .totalAmount(totalAmount)
                .build();
    }
}
