package kr.hhplus.be.server.interfaces.api.user;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class UserResponse {

    public record Balance(Long userId, int balance) {
        @Builder
        public Balance {}
    }

    public record UserCoupons(Long userId, List<UserCoupon> userCoupons) {
        @Builder
        public UserCoupons{}
    }

    public static class UserCoupon {
        private Long couponId;
        private String name;
        private BigDecimal amount;
        private LocalDateTime validStartDate;
        private LocalDateTime validEndDate;
        private String status;
    }

}
