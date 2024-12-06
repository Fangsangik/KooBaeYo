package com.example.koobaeyo.orders.dto;

import com.example.koobaeyo.menus.entity.Menu;
import lombok.Getter;

@Getter
public class StoreMenuResponse {

    private String name;

    private String description;

    private Double price;

    public StoreMenuResponse(Menu menu){
        name = menu.getName();
        description = menu.getDescription();
        price = menu.getPrice();
    }
}
