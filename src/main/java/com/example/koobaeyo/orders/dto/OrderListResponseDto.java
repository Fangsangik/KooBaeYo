package com.example.koobaeyo.orders.dto;

import com.example.koobaeyo.orders.type.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderListResponseDto {

    private Long orderId;
    private Long storeId;
    private Long userId;
    private Long menuId;
    private Integer quantity;
    private Double totalPrice;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;


    public OrderListResponseDto(Long orderId, Long storeId, Long userId, Long menuId, Integer quantity, Double totalPrice, OrderStatus orderStatus, LocalDateTime createdAt) {
        this.orderId = orderId;
        this.storeId = storeId;
        this.userId = userId;
        this.menuId = menuId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
    }
}
