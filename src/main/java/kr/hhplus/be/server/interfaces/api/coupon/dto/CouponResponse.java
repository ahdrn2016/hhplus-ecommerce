package kr.hhplus.be.server.interfaces.api.coupon.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CouponResponse {

    private Long couponId;
    private String name;
    private BigDecimal amount;
    private LocalDateTime validStartDate;
    private LocalDateTime validEndDate;

}
