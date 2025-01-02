package kr.hhplus.be.server.interfaces.api.user.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class UserCouponInfo {

    private Long couponId;
    private String name;
    private BigDecimal amount;
    private LocalDateTime validStartDate;
    private LocalDateTime validEndDate;
    private UserCouponStatus status;

}
