package com.example.koobaeyo.orders.dto;

import com.example.koobaeyo.menus.entity.Menu;
import com.example.koobaeyo.orders.entity.Order;
import com.example.koobaeyo.orders.type.OrderStatus;
import com.example.koobaeyo.stores.entity.Store;
import com.example.koobaeyo.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderRequestDto {
    @NotBlank(message = "Store ID cannot be blank")
    private Long storeId;
    @NotBlank(message = "Menu ID cannot be blank")
    private Long menuId;
    private Integer quantity;
    private OrderStatus status;

    public Order toEntity(User user, Store store, Menu menu) {
        return Order.builder()
                .user(user)
                .menu(menu)
                .store(store)
                .quantity(this.quantity)
                .orderStatus(this.status)
                .build();
    }
}
