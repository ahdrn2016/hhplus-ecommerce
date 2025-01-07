package kr.hhplus.be.server.domain.user;

import kr.hhplus.be.server.application.user.UserResult;
import kr.hhplus.be.server.interfaces.api.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserResponse.Balance getBalance(Long userId) {
        User user = userRepository.findBalanceByUserId(userId);
        return UserInfo.toBalanceResponse(user);
    }

    public UserResult.Balance chargeBalance(UserCommand.ChargeBalance command) {
        User user = userRepository.findBalanceByUserId(command.userId());
        user.addBalance(command.amount());
        return UserInfo.toBalanceResult(user);
    }

}
