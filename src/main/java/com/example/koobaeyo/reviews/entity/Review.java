package com.example.koobaeyo.reviews.entity;

import com.example.koobaeyo.common.BaseEntity;
import com.example.koobaeyo.orders.entity.Order;
import com.example.koobaeyo.stores.entity.Store;
import com.example.koobaeyo.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rate;
    private String content;

    public Review() {
    }

    @Builder
    public Review(Order order, Store store, User user, String content, int rate) {
        this.order = order;
        this.store = store;
        this.user = user;
        this.content = content;
        this.rate = rate;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
}
