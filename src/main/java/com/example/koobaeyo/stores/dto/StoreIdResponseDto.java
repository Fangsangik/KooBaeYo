package com.example.koobaeyo.stores.dto;

import com.example.koobaeyo.stores.entity.Store;
import lombok.Getter;

/**
 * 가게 아이디 반환 Dto
 */
@Getter
public class StoreIdResponseDto {
    private Long id;


    public StoreIdResponseDto(Store store){
        this.id = store.getId();
    }
}
