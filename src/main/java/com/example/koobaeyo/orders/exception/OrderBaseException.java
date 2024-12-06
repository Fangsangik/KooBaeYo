package com.example.koobaeyo.orders.exception;

import com.example.koobaeyo.orders.exception.type.OrderErrorCode;
import lombok.Getter;

@Getter
public class OrderBaseException extends RuntimeException {
    private final OrderErrorCode errorCode;

    public OrderBaseException(OrderErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public OrderBaseException(String message, OrderErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
