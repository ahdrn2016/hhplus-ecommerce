package kr.hhplus.be.server.domain.user;

import kr.hhplus.be.server.interfaces.api.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BalanceHistoryRepository balanceHistoryRepository;

    public UserResponse.UserDto getUser(Long userId) {
        User user = userRepository.findById(userId);
        return UserInfo.toResponse(user);
    }

    public UserResponse.UserDto chargeBalance(Long userId, int amount) {
        User user = userRepository.findById(userId);
        user.addBalance(amount);
        return UserInfo.toResponse(user);
    }

    public UserResponse.UserDto useBalance(Long userId, int amount) {
        User user = userRepository.findById(userId);
        user.minusBalance(amount);
        return UserInfo.toResponse(user);
    }

    public BalanceHistory setBalanceHistory(Long userId, BalanceHistoryType type, int amount) {
        BalanceHistory balanceHistory = BalanceHistory.create(userId, type, amount);
        return balanceHistoryRepository.save(balanceHistory);
    }
}
