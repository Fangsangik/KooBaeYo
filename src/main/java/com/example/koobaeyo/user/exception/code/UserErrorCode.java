package com.example.koobaeyo.user.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode {

    IMPROPER_PASSWORD(HttpStatus.FORBIDDEN,"비밀번호는 최소 8자, 대소문자 포함한 영문, 숫자, 특수문자를 포함해야합니다."),
    DUPLICATED_EMAIL(HttpStatus.FORBIDDEN,"중복된 이메일입니다."),
    CANNOT_FIND_ID(HttpStatus.FORBIDDEN, "사용자를 찾을 수 없습니다."),
    PASSWORD_DOES_NOT_MATCH(HttpStatus.NOT_FOUND, "비밀번호가 일치하지 않습니다."),
    USE_PROPER_ID(HttpStatus.FORBIDDEN, "본인의 아이디를 입력해주세요.");

    private final HttpStatus httpStatus;
    private final String message;
}
