package kr.hhplus.be.server.domain.order;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private Long productId;

    private int price;

    private int quantity;

    @Builder
    public OrderProduct(Order order, Long productId, int price, int quantity) {
        this.order = order;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }
}
