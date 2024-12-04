package com.example.koobaeyo.stores.dto;

import com.example.koobaeyo.menus.entity.Menu;
import com.example.koobaeyo.stores.entity.Store;
import com.example.koobaeyo.stores.entity.type.CuisineType;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Getter
public class StoreResponseDetailDto {
    private String storeName;
    private String ownerName;
    private String ownerPhone;
    private CuisineType type;
    private Double minOrderAmount;
    private LocalTime opening;
    private LocalTime closing;
    private List<Menu> menus;

    public StoreResponseDetailDto(Store store){
        this.storeName = store.getName();
        this.ownerName = store.getOwner().getName();
        this.ownerPhone = store.getOwner().getNumber();
        this.type = store.getType();
        this.minOrderAmount = store.getMinOrderAmount();
        this.opening = store.getOpening();
        this.closing = store.getClosing();
        this.menus = store.getMenus();
    }
}
