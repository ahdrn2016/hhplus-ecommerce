package kr.hhplus.be.server.domain.order;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.BaseEntity;
import kr.hhplus.be.server.domain.coupon.CouponCommand;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private Long couponId;
    private int totalAmount;
    private int discountAmount;
    private int paymentAmount;

    @OneToMany(mappedBy = "orders")
    private List<OrderProduct> orderProducts;

    @Builder
    public Order(Long userId, Long couponId, int totalAmount, int discountAmount, int paymentAmount) {
        this.userId = userId;
        this.couponId = couponId;
        this.totalAmount = totalAmount;
        this.discountAmount = discountAmount;
        this.paymentAmount = paymentAmount;
    }

    public static Order create(Long userId, CouponCommand.OrderCoupon userCoupon, int totalAmount) {
        return Order.builder()
                .userId(userId)
                .couponId(userCoupon.couponId())
                .totalAmount(totalAmount)
                .discountAmount(userCoupon.amount())
                .paymentAmount(totalAmount - userCoupon.amount())
                .build();
    }
}
