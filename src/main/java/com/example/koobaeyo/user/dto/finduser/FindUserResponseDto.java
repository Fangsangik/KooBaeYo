package com.example.koobaeyo.user.dto.finduser;

import com.example.koobaeyo.user.type.Role;
import lombok.Getter;

@Getter
public class FindUserResponseDto {

    private final Long id;

    private final String name;

    private final String email;

    private final Role role;

    public FindUserResponseDto(Long id, String name, String email, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }
}
