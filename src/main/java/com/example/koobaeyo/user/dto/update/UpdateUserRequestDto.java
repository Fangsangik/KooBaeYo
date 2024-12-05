package com.example.koobaeyo.user.dto.update;

import lombok.Getter;

@Getter
public class UpdateUserRequestDto {

    private final String password;

    private final String newName;

    private final String newEmail;

    private final String newPassword;

    public UpdateUserRequestDto(String password, String newName, String newEmail, String newPassword) {
        this.password = password;
        this.newName = newName;
        this.newEmail = newEmail;
        this.newPassword = newPassword;
    }
}
