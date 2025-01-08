package kr.hhplus.be.server.interfaces.api.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import kr.hhplus.be.server.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping(path = "/balance/{userId}", produces = { "application/json" })
    @Operation(summary = "잔액 조회", description = "유저의 잔액을 조회합니다.", tags = { "user" })
    public ResponseEntity<UserResponse.Balance> getBalance(
            @PathVariable Long userId
    ) {
        UserResponse.Balance response = userService.getBalance(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/balance/charge", produces = { "application/json" }, consumes = { "application/json" })
    @Operation(summary = "잔액 충전", description = "유저의 잔액을 충전합니다.", tags = { "user" })
    public ResponseEntity<UserResponse.Balance> chargeBalance(
            @RequestBody UserRequest.ChargeBalance request
    ) {
        UserResponse.Balance response = userService.chargeBalance(request.toCommand());
        return ResponseEntity.ok(response);
    }

}
