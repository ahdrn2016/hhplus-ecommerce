package kr.hhplus.be.server.support.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    INVALID_CHARGE_AMOUNT(HttpStatus.BAD_REQUEST, "0원 이상부터 충전 가능합니다."),

    DUPLICATE_ISSUE_COUPON(HttpStatus.BAD_REQUEST, "이미 발급된 쿠폰입니다."),
    SOLD_OUT_COUPON(HttpStatus.BAD_REQUEST, "쿠폰이 모두 소진되었습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
