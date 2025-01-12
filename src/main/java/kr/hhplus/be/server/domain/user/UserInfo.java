package kr.hhplus.be.server.domain.user;

import kr.hhplus.be.server.interfaces.api.user.UserResponse;

public class UserInfo {

    public static UserResponse.UserDto toResponse(
            User user
    ) {
        return UserResponse.UserDto.builder()
                .userId(user.getId())
                .balance(user.getBalance())
                .build();
    }

}
