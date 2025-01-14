package kr.hhplus.be.server.infrastructure.user;

import kr.hhplus.be.server.domain.user.BalanceHistory;
import kr.hhplus.be.server.domain.user.BalanceHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BalanceHistoryRepositoryImpl implements BalanceHistoryRepository {

    private final BalanceHistoryJpaRepository balanceHistoryJpaRepository;

    @Override
    public BalanceHistory save(BalanceHistory balanceHistory) {
        return balanceHistoryJpaRepository.save(balanceHistory);
    }

}
