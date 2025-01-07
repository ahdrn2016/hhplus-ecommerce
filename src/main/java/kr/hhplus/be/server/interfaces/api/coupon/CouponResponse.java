package kr.hhplus.be.server.interfaces.api.coupon;

import java.time.LocalDateTime;

public class CouponResponse {

    public record issueCoupon(Long couponId, String name, int amount, LocalDateTime validStartDate, LocalDateTime validEndDate) {}

}
