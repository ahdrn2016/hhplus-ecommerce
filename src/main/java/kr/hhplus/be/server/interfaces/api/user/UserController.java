package kr.hhplus.be.server.interfaces.api.user;

import io.swagger.v3.oas.annotations.Operation;
import kr.hhplus.be.server.application.user.UserFacade;
import kr.hhplus.be.server.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final UserFacade userFacade;

    @GetMapping(path = "/balance/{userId}", produces = { "application/json" })
    @Operation(summary = "잔액 조회", description = "유저의 잔액을 조회합니다.", tags = { "user" })
    public ResponseEntity<UserResponse.UserDto> getBalance(
            @PathVariable Long userId
    ) {
        UserResponse.UserDto response = userService.getUser(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/balance/charge", produces = { "application/json" }, consumes = { "application/json" })
    @Operation(summary = "잔액 충전", description = "유저의 잔액을 충전합니다.", tags = { "user" })
    public ResponseEntity<UserResponse.UserDto> chargeBalance(
            @RequestBody UserRequest.ChargeBalance request
    ) {
        UserResponse.UserDto response = userFacade.chargeBalance(request.toParam());
        return ResponseEntity.ok(response);
    }

}
