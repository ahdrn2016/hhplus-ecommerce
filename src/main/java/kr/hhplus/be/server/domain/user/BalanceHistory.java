package kr.hhplus.be.server.domain.user;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class BalanceHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private BalanceHistoryType type;

    private int amount;

    @Builder
    public BalanceHistory(Long userId, BalanceHistoryType type, int amount) {
        this.userId = userId;
        this.type = type;
        this.amount = amount;
    }

    public static BalanceHistory create(Long userId, BalanceHistoryType type, int amount) {
        return BalanceHistory.builder()
                .userId(userId)
                .type(type)
                .amount(amount)
                .build();
    }
}
