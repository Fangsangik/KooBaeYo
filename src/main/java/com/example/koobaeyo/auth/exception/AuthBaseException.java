package com.example.koobaeyo.auth.exception;

import com.example.koobaeyo.auth.exception.type.AuthErrorCode;
import lombok.Getter;

@Getter
public class AuthBaseException extends RuntimeException {
    private final AuthErrorCode errorCode;

    public AuthBaseException(AuthErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public AuthBaseException(String message, AuthErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
