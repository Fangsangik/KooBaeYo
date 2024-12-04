package com.example.koobaeyo.stores.entity;

import com.example.koobaeyo.common.BaseEntity;
import com.example.koobaeyo.stores.dto.StoreRemodelRequestDto;
import com.example.koobaeyo.stores.entity.type.CuisineType;
import com.example.koobaeyo.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;

@Getter
@Entity
@Table(
        name = "stores"
)
public class Store extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CuisineType type;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, columnDefinition = "DOUBLE")
    private Double minOrderAmount;

    @Column(nullable = false)
    private LocalTime opening;

    @Column(nullable = false)
    private LocalTime closing;

    @Column(columnDefinition = "TINYINT(1) NOT NULL DEFAULT 1")
    private Boolean isOpen;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Menu> menus = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    public Store(){ }

    @Builder
    public Store(String name, CuisineType type, String address, Double minOrderAmount, LocalTime opening,
                 LocalTime closing, Boolean isOpen, User user) {

        this.name = name;
        this.type = type;
        this.address = address;
        this.minOrderAmount = minOrderAmount;
        this.opening = opening;
        this.closing = closing;
        this.isOpen = isOpen;
        this.owner = user;
    }

    public void remodel(StoreRemodelRequestDto dto){
        this.name = dto.getName();
        this.type = dto.getType();
        this.address = dto.getAddress();
    }

    public void closeDown(){
        this.isOpen = false;
    }

}
