package kr.hhplus.be.server.domain.order;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

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

    private BigDecimal totalAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts;

    @Builder
    public Order(Long userId, OrderStatus status, BigDecimal totalAmount, List<OrderProduct> orderProducts) {
        this.userId = userId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.orderProducts = orderProducts;
    }

    public static Order create(Long userId, List<OrderProduct> orderProducts) {
        return Order.builder()
                .userId(userId)
                .status(OrderStatus.WAITING)
                .totalAmount(orderProducts.stream()
                        .map(op -> op.getPrice().multiply(BigDecimal.valueOf(op.getQuantity())))
                                .reduce(BigDecimal.ZERO, BigDecimal::add))
                .orderProducts(orderProducts)
                .build();
    }

    public void complete() {
        this.status = OrderStatus.COMPLETE;
    }
}
