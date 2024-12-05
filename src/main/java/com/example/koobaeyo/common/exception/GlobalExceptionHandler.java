package com.example.koobaeyo.common.exception;

import com.example.koobaeyo.auth.exception.AuthBaseException;
import com.example.koobaeyo.common.exception.response.ErrorResponse;
import com.example.koobaeyo.stores.exception.StoreBaseException;
import com.example.koobaeyo.user.exception.UserBaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleStoreException(StoreBaseException e){
        log.error("StoreBaseException : {}", e.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse,e.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleAuthException(AuthBaseException e){
        log.error("AuthBaseException : {}", e.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse,e.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleUserException(UserBaseException e){
        log.error("UserBaseException : {}", e.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse,e.getErrorCode().getHttpStatus());
    }
}
