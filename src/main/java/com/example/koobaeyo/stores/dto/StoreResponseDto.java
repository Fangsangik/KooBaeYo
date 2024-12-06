package com.example.koobaeyo.stores.dto;

import com.example.koobaeyo.stores.entity.Store;
import com.example.koobaeyo.stores.entity.type.CuisineType;
import lombok.Getter;

/**
 * 가게 목록 조회에 들어갈 가게 정보 Dto
 */
@Getter
public class StoreResponseDto {
    private Long id;
    private String name;
    private CuisineType type;
    private String address;


    public StoreResponseDto(Store store){
        this.id = store.getId();
        this.name = store.getName();
        this.type = store.getType();
        this.address = store.getAddress();
    }
}
