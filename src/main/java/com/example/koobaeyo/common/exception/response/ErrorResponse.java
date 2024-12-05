package com.example.koobaeyo.common.exception.response;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private boolean success;
    private String message;

    public ErrorResponse(String message){
        this.success = false;
        this.message = message;
    }
    public ErrorResponse(){
        this.success = false;
    }

    public void putMessage(String message){
        this.message =message;
    }
}
