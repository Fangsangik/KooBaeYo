package com.example.koobaeyo.orders.dto;

import lombok.Getter;

@Getter
public class OrderListResponseDto {

    private Long orderId;
    private Long storeId;
    private Long userId;
    private Long menuId;
    private Integer quantity;
    private Long totalPrice;
    private String orderStatus;


    public OrderListResponseDto(Long orderId, Long storeId, Long userId, Long menuId, Integer quantity, Long totalPrice, String orderStatus) {
        this.orderId = orderId;
        this.storeId = storeId;
        this.userId = userId;
        this.menuId = menuId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
    }
}
