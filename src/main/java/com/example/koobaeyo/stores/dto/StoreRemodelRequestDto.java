package com.example.koobaeyo.stores.dto;

import com.example.koobaeyo.stores.entity.type.CuisineType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

/**
 * 가게 리모델링 요청 Dto
 */
@Getter
public class StoreRemodelRequestDto {

    @NotBlank
    private String name;
    @NotNull
    private CuisineType type;
    @NotBlank
    private String address;

    public StoreRemodelRequestDto(String name, CuisineType type, String address){
        this.name = name;
        this.type =type;
        this.address = address;
    }
}
