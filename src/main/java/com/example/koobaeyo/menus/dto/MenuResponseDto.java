package com.example.koobaeyo.menus.dto;

import lombok.Getter;

@Getter
public class MenuResponseDto {

    private Long id;

    public MenuResponseDto(Long id) {
        this.id = id;
    }
}
