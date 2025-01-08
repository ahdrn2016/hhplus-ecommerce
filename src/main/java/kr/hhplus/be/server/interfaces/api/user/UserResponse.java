package kr.hhplus.be.server.interfaces.api.user;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class UserResponse {

    public record Balance(Long userId, int balance) {
        @Builder
        public Balance {}
    }

    public record UserCoupon(Long userId, Coupon coupon) {
        @Builder
        public UserCoupon{}
    }

    public static class Coupon {
        private Long couponId;
        private String name;
        private BigDecimal amount;
        private LocalDateTime validStartDate;
        private LocalDateTime validEndDate;
        private String status;
    }

}
