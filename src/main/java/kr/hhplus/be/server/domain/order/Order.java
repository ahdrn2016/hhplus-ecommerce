package kr.hhplus.be.server.domain.order;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.BaseEntity;
import kr.hhplus.be.server.domain.coupon.CouponCommand;
import kr.hhplus.be.server.domain.coupon.CouponInfo;
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
    private Long couponId;
    private int totalAmount;
    private int discountAmount;
    private int paymentAmount;

    @Builder
    public Order(Long userId, Long couponId, int totalAmount, int discountAmount) {
        this.userId = userId;
        this.couponId = couponId;
        this.totalAmount = totalAmount;
        this.discountAmount = discountAmount;
        this.paymentAmount = totalAmount - discountAmount;
    }

    public static Order create(Long userId, CouponInfo.UserCouponDto userCouponDto, int totalAmount) {
        Long couponId = userCouponDto != null ? userCouponDto.couponId() : null;
        int discountAmount = userCouponDto != null ? userCouponDto.amount() : null;

        return Order.builder()
                .userId(userId)
                .couponId(couponId)
                .totalAmount(totalAmount)
                .discountAmount(discountAmount)
                .build();
    }
}
