package com.example.koobaeyo.menus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class MenuRequestDto {

    @NotBlank
    @NotNull
    private String name;

    @NotBlank
    @NotNull
    private String description;

    @NotNull
    @Positive  // 양수인지 확인
    private double price;

    public MenuRequestDto(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
