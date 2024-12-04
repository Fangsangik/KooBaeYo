package com.example.koobaeyo.stores.dto;

import com.example.koobaeyo.stores.entity.type.CuisineType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class StoreRemodelRequestDto {

    @NotBlank
    private String name;
    @NotBlank
    private CuisineType type;
    @NotBlank
    private String address;

    public StoreRemodelRequestDto(String name, CuisineType type, String address){
        this.name = name;
        this.type =type;
        this.address = address;
    }
}
