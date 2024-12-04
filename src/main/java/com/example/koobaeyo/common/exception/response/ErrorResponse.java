package com.example.koobaeyo.common.exception.response;


public class ErrorResponse{
    private boolean success;
    private String message;

    public ErrorResponse(String message){
        this.success = false;
        this.message = message;
    }

}
