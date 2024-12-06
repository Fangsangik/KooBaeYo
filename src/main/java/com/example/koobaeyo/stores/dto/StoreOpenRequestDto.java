package com.example.koobaeyo.stores.dto;

import com.example.koobaeyo.stores.constants.StoreConstants;
import com.example.koobaeyo.stores.entity.Store;
import com.example.koobaeyo.stores.entity.type.CuisineType;
import com.example.koobaeyo.user.entity.User;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalTime;

/**
 * 가게 오픈 요청 Dto
 */
@Getter
public class StoreOpenRequestDto {

    @NotBlank
    private String name;
    @NotNull
    private CuisineType type;
    @NotBlank
    private String address;
    @DecimalMin(StoreConstants.MIN_PRICE)
    private Double minOrderAmount;
    @NotNull
    private LocalTime opening;
    @NotNull
    private LocalTime closing;

    public Store toEntity(User user){
        return Store.builder()
                .name(this.name)
                .type(this.type)
                .user(user)
                .address(this.address)
                .minOrderAmount(this.minOrderAmount)
                .opening(this.opening)
                .closing(this.closing).build();
    }
}
