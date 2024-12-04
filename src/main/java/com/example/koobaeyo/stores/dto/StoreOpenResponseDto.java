package com.example.koobaeyo.stores.dto;

import com.example.koobaeyo.stores.entity.Store;
import lombok.Getter;

@Getter
public class StoreOpenResponseDto {
    private Long id;


    public StoreOpenResponseDto(Store store){
        this.id = store.getId();
    }
}
