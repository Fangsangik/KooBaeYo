package com.example.koobaeyo.reviews.exception;

import com.example.koobaeyo.reviews.exception.type.ReviewErrorCode;
import com.example.koobaeyo.stores.exception.code.StoreErrorCode;

public class ReviewBaseException extends RuntimeException{
    private final ReviewErrorCode errorCode;

    public ReviewBaseException(ReviewErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ReviewBaseException(String message, ReviewErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
