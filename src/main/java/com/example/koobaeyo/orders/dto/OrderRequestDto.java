package com.example.koobaeyo.orders.dto;

import com.example.koobaeyo.orders.entity.Order;
import com.example.koobaeyo.orders.type.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderRequestDto {
    private Long userId;
    private Long storeId;
    private Long menuId;
    private Integer quantity;
    private Long totalPrice;
    private OrderStatus status;

    //생성자 따로 추가
}
