package kr.hhplus.be.server.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BalanceHistoryRepository balanceHistoryRepository;

    public UserInfo.UserDto getUser(Long userId) {
        User user = userRepository.findById(userId);
        return UserInfo.of(user);
    }

    public UserInfo.UserDto chargeBalance(Long userId, int amount) {
        User user = userRepository.findById(userId);
        user.addBalance(amount);
        return UserInfo.of(user);
    }

    public UserInfo.UserDto useBalance(Long userId, int amount) {
        User user = userRepository.findById(userId);
        user.minusBalance(amount);
        return UserInfo.of(user);
    }

    public void setBalanceHistory(Long userId, BalanceHistoryType type, int amount) {
        BalanceHistory balanceHistory = BalanceHistory.create(userId, type, amount);
        balanceHistoryRepository.save(balanceHistory);
    }
}
