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

    private Long couponId;

    @Enumerated(EnumType.STRING)
    private UserCouponStatus status;

    @Builder
    public UserCoupon(Long userId, Long couponId, UserCouponStatus status) {
        this.userId = userId;
        this.couponId = couponId;
        this.status = status;
    }

    public static UserCoupon create(Long userId, Long couponId, UserCouponStatus status) {
        return UserCoupon.builder()
                .userId(userId)
                .couponId(couponId)
                .status(status)
                .build();
    }

    public void used() {
        this.status = UserCouponStatus.USED;
    }
}
