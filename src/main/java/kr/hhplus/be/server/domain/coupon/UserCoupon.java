package kr.hhplus.be.server.domain.coupon;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class UserCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Enumerated(EnumType.STRING)
    private UserCouponStatus status;

    @Builder
    public UserCoupon(Long userId, Coupon coupon, UserCouponStatus status) {
        this.userId = userId;
        this.coupon = coupon;
        this.status = status;
    }

    public static UserCoupon create(Long userId, Coupon coupon, UserCouponStatus status) {
        return UserCoupon.builder()
                .userId(userId)
                .coupon(coupon)
                .status(status)
                .build();
    }

    public void used() {
        this.status = UserCouponStatus.USED;
    }
}
