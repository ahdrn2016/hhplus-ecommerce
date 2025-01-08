package kr.hhplus.be.server.domain.user;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.coupon.UserCoupon;
import kr.hhplus.be.server.support.exception.CustomException;
import kr.hhplus.be.server.support.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int balance;

    @OneToMany(mappedBy = "user")
    private List<UserCoupon> userCoupons;

    @Builder
    public User(Long id, int balance) {
        this.id = id;
        this.balance = balance;
    }

    public static User create(long id, int balance) {
        return User.builder()
                .id(id)
                .balance(balance)
                .build();
    }

    public void addBalance(int amount) {
        if (amount <= 0) {
            throw new CustomException(ErrorCode.INVALID_CHARGE_AMOUNT);
        }

        this.balance += amount;
    }
}
