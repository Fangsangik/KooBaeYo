package com.example.koobaeyo.auth.exception.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode {

    PASSWORD_DOES_NOT_MATCH(HttpStatus.UNAUTHORIZED, "잘못된 비밀번호입니다."),
    CANNOT_FIND_USER(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    DELETED_USER(HttpStatus.FORBIDDEN, "사용자를 찾을 수 없습니다."),
    CAN_NOT_LOGIN(HttpStatus.FORBIDDEN, "이미 로그인된 회원이 있습니다." ),
    SHOULD_HAVE_LOGINED(HttpStatus.UNAUTHORIZED, "로그인 후 할 수 있는 작업입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
