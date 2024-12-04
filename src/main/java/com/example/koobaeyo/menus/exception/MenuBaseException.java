package com.example.koobaeyo.menus.exception;

import com.example.koobaeyo.menus.exception.type.MenuErrorCode;

public class MenuBaseException extends RuntimeException{
    private final MenuErrorCode errorCode;

    public MenuBaseException(MenuErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public MenuBaseException(String message, MenuErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
