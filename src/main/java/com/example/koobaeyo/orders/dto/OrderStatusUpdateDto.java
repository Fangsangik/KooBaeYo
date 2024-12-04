package com.example.koobaeyo.orders.dto;

import com.example.koobaeyo.orders.type.OrderStatus;
import lombok.Getter;

@Getter
public class OrderStatusUpdateDto {
    private Long orderId;
    private OrderStatus orderStatus;

    public OrderStatusUpdateDto(Long orderId, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
    }

    public Long getOrderId() {
        return orderId;
    }


    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}
