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
        User user = userRepository.findBalanceByUserId(userId);
        return UserInfo.toResponse(user);
    }

    public UserResponse.Balance chargeBalance(Long userId, int amount) {
        User user = userRepository.findBalanceByUserId(userId);
        user.addBalance(amount);
        return UserInfo.toResponse(user);
    }

    public UserResponse.Balance useBalance(Long userId, int amount) {
        User user = userRepository.findBalanceByUserId(userId);
        user.minusBalance(amount);
        return UserInfo.toResponse(user);
    }

    public BalanceHistory setBalanceHistory(Long userId, BalanceHistoryType type, int amount) {
        BalanceHistory balanceHistory = BalanceHistory.create(userId, type, amount);
        return balanceHistoryRepository.save(balanceHistory);
    }
}
