package kr.hhplus.be.server.domain.point;

import jakarta.persistence.*;
import kr.hhplus.be.server.support.exception.CustomException;
import kr.hhplus.be.server.support.exception.ErrorCode;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private BigDecimal point;

    @Version
    private Integer version;

    public void add(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new CustomException(ErrorCode.INVALID_CHARGE_AMOUNT);
        this.point = this.point.add(amount);
    }

    public void minus(BigDecimal amount) {
        if (this.point.compareTo(amount) < 0) throw new CustomException(ErrorCode.INSUFFICIENT_POINT);
        this.point = this.point.subtract(amount);
    }

}
