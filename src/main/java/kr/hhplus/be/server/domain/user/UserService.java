package kr.hhplus.be.server.domain.user;

import kr.hhplus.be.server.interfaces.api.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BalanceHistoryRepository balanceHistoryRepository;

    public UserResponse.Balance getBalance(Long userId) {
        // 유저 정보 조회
        User user = userRepository.findBalanceByUserId(userId);
        return UserInfo.toResponse(user);
    }

    public UserResponse.Balance chargeBalance(UserCommand.ChargeBalance command) {
        Long userId = command.userId();
        int amount = command.amount();

        // 유저 정보 조회 후 잔액 충전
        User user = userRepository.findBalanceByUserId(userId);
        user.addBalance(amount);

        // 잔액 충전 기록
        BalanceHistory balanceHistory = BalanceHistory.create(userId, BalanceHistoryType.CHARGE, amount);
        balanceHistoryRepository.save(balanceHistory);

        return UserInfo.toResponse(user);
    }

    public void useBalance(Long userId, int amount) {
        // 잔액 사용
        User user = userRepository.findBalanceByUserId(userId);
        user.minusBalance(amount);

        // 잔액 사용 기록
        BalanceHistory balanceHistory = BalanceHistory.create(userId, BalanceHistoryType.USE, amount);
        balanceHistoryRepository.save(balanceHistory);
    }
}
