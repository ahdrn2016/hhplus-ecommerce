package kr.hhplus.be.server.domain.coupon;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class IssuedCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long couponId;

    private int amount;

    @Enumerated(EnumType.STRING)
    private IssuedCouponStatus status;

    @Builder
    public IssuedCoupon(Long userId, Long couponId, int amount, IssuedCouponStatus status) {
        this.userId = userId;
        this.couponId = couponId;
        this.amount = amount;
        this.status = status;
    }

    public static IssuedCoupon create(Long userId, Long couponId, int amount, IssuedCouponStatus status) {
        return IssuedCoupon.builder()
                .userId(userId)
                .couponId(couponId)
                .amount(amount)
                .status(status)
                .build();
    }

    public void used() {
        this.status = IssuedCouponStatus.USED;
    }
}
