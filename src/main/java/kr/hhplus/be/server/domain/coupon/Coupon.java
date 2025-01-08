package kr.hhplus.be.server.domain.coupon;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int amount;

    private LocalDateTime validStartDate;

    private LocalDateTime validEndDate;

    private int quantity;

    @OneToMany(mappedBy = "coupon")
    private List<UserCoupon> userCoupons;

}
