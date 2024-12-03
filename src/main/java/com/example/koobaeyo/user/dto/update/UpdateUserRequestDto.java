package com.example.koobaeyo.user.dto.update;

import lombok.Getter;

@Getter
public class UpdateUserRequestDto {

    private final String password;

    private final String newPassword;

    public UpdateUserRequestDto(String password, String newPassword) {
        this.password = password;
        this.newPassword = newPassword;
    }
}
