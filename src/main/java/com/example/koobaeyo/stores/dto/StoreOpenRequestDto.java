package com.example.koobaeyo.stores.dto;

import com.example.koobaeyo.stores.entity.Store;
import com.example.koobaeyo.stores.entity.type.CuisineType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class StoreOpenRequestDto {

    @NotBlank
    private String name;
    @NotBlank
    private CuisineType type;
    @NotBlank
    private String address;
    @NotBlank
    private Double minOrderAmount;
    @NotBlank
    private LocalTime opening;
    @NotBlank
    private LocalTime closing;

    public Store toEntity(){
        return Store.builder()
                .name(this.name)
                .type(this.type)
                .address(this.address)
                .minOrderAmount(this.minOrderAmount)
                .opening(this.opening)
                .closing(this.closing).build();
    }
}
