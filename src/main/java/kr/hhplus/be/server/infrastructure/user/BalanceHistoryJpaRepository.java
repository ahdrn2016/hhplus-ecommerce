package kr.hhplus.be.server.infrastructure.user;

import kr.hhplus.be.server.domain.user.BalanceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceHistoryJpaRepository extends JpaRepository<BalanceHistory, Long> {

}
