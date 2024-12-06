package com.example.koobaeyo.orders.dto;

import com.example.koobaeyo.orders.type.OrderStatus;
import lombok.Getter;

@Getter
public class OrderUpdateResponseDto {

    private Long id;
    private OrderStatus orderStatus;

    public OrderUpdateResponseDto(Long id, OrderStatus orderStatus) {
        this.id = id;
        this.orderStatus = orderStatus;
    }
}
