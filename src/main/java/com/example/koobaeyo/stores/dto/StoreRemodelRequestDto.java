package com.example.koobaeyo.stores.dto;

import com.example.koobaeyo.stores.entity.type.CuisineType;
import lombok.Getter;

@Getter
public class StoreRemodelRequestDto {

    private String name;
    private CuisineType type;
    private String address;

    public StoreRemodelRequestDto(String name, CuisineType type, String address){
        this.name = name;
        this.type =type;
        this.address = address;
    }
}
