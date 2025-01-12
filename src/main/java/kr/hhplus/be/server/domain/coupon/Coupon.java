package kr.hhplus.be.server.domain.coupon;

import jakarta.persistence.*;
import kr.hhplus.be.server.support.exception.CustomException;
import kr.hhplus.be.server.support.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int amount;

    private LocalDateTime validStartDate;

    private LocalDateTime validEndDate;

    private int quantity;

    @Builder
    public Coupon(Long id, String name, int amount, LocalDateTime validStartDate, LocalDateTime validEndDate, int quantity) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.validStartDate = validStartDate;
        this.validEndDate = validEndDate;
        this.quantity = quantity;
    }

    public static Coupon create(Long couponId, String name, int amount, LocalDateTime validStartDate, LocalDateTime validEndDate, int quantity) {
        return Coupon.builder()
                .id(couponId)
                .name(name)
                .amount(amount)
                .validStartDate(validStartDate)
                .validEndDate(validEndDate)
                .quantity(quantity)
                .build();
    }

    public void issue() {
        if (this.quantity <= 0) {
            throw new CustomException(ErrorCode.SOLD_OUT_COUPON);
        }

        this.quantity = this.quantity - 1;
    }
}
