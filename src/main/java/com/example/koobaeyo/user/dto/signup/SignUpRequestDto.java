package com.example.koobaeyo.user.dto.signup;


import com.example.koobaeyo.user.entity.User;
import com.example.koobaeyo.user.type.Role;
import lombok.Getter;


@Getter
public class SignUpRequestDto {
    private final String name;

    private final String email;

    private final String password;

    private final Role role;

    private final String number;


    public SignUpRequestDto(String name, String email, String password, Role role, String number) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.number = number;
    }

    public User toEntity(String password) {
        return new User(
                this.name,
                this.email,
                password,
                this.role,
                this.number
        );
    }
}
