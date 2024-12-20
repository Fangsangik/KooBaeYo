package com.example.koobaeyo.stores.exception;

import com.example.koobaeyo.stores.exception.code.StoreErrorCode;
import lombok.Getter;

/**
 * 가게공통 예외
 */
@Getter
public class StoreBaseException extends RuntimeException {
    private final StoreErrorCode errorCode;

    public StoreBaseException(StoreErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public StoreBaseException(String message, StoreErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
