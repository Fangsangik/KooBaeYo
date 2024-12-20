package com.example.koobaeyo.menus.entity;

import com.example.koobaeyo.common.BaseEntity;
import com.example.koobaeyo.stores.entity.Store;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name="menus")
public class Menu  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stores_id", nullable = false)
    private Store store;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double price;


    public Menu(Store store, String name, String description, Double price) {
        this.store = store;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public void update(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    protected Menu() {
    }
}
