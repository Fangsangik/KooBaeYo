package com.example.koobaeyo.orders.entity;

import com.example.koobaeyo.common.BaseEntity;
import com.example.koobaeyo.menus.entity.Menu;
import com.example.koobaeyo.orders.type.OrderStatus;
import com.example.koobaeyo.stores.entity.Store;
import com.example.koobaeyo.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


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
    private OrderStatus orderStatus = OrderStatus.ORDERED;

    public Order() {}


    @Builder
    public Order(User user, Menu menu, Store store, Integer quantity,Double totalPrice, OrderStatus orderStatus) {
        this.user = user;
        this.menu = menu;
        this.store = store;
        this.quantity = quantity;
        this.totalPrice = calculateTotalPrice();
        this.orderStatus = orderStatus;
    }

    private Double calculateTotalPrice() {
        if (menu != null && menu.getPrice() != null && quantity != null) {
            return menu.getPrice() * quantity.doubleValue();
        }else {
            return 0.0;
        }
    }
}
