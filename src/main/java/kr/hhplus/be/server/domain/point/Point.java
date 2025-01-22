package kr.hhplus.be.server.domain.point;

import jakarta.persistence.*;
import kr.hhplus.be.server.support.exception.CustomException;
import kr.hhplus.be.server.support.exception.ErrorCode;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private int point;

    @Version
    private Integer version;

    public void add(int amount) {
        if (amount <= 0) throw new CustomException(ErrorCode.INVALID_CHARGE_AMOUNT);
        this.point += amount;
    }

    public void minus(int amount) {
        if (this.point < amount) throw new CustomException(ErrorCode.INSUFFICIENT_POINT);
        this.point -= amount;
    }

}
