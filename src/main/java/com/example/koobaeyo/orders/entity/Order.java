package com.example.koobaeyo.orders.entity;

import com.example.koobaeyo.common.BaseEntity;
import com.example.koobaeyo.orders.type.OrderStatus;
import com.example.koobaeyo.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.Store;

import java.awt.*;

@Entity
@Setter
@Getter
@Table(name = "Orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne   //(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name =  "menu_id")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    private Integer quantity;

    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public Order() {}

    @Builder
    public Order(User user, Menu menu, Store store, Integer quantity, Double totalPrice, OrderStatus orderStatus) {
        this.user = user;
        this.menu = menu;
        this.store = store;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
    }
}
