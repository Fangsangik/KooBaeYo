package com.example.koobaeyo.orders.dto;

import lombok.Getter;

@Getter
public class OrderResponseDto {

    private Long orderId;

    public OrderResponseDto(Long orderId) {
        this.orderId = orderId;
    }
}
