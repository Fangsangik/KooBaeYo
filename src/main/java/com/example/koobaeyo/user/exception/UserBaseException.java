package com.example.koobaeyo.user.exception;

import com.example.koobaeyo.user.exception.code.UserErrorCode;
import lombok.Getter;

@Getter
public class UserBaseException extends RuntimeException {
    private final UserErrorCode errorCode;

    public UserBaseException(UserErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public UserBaseException(String message, UserErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
