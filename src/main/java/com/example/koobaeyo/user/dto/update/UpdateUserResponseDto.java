package com.example.koobaeyo.user.dto.update;

import lombok.Getter;

@Getter
public class UpdateUserResponseDto {

    private final Long id;

    private final String newName;

    private final String newEmail;


    public UpdateUserResponseDto(Long id, String newName, String newEmail) {
        this.id = id;
        this.newName = newName;
        this.newEmail = newEmail;
    }
}
