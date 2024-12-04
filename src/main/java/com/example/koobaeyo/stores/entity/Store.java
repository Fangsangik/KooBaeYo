package com.example.koobaeyo.stores.entity;

import com.example.koobaeyo.common.BaseEntity;
import com.example.koobaeyo.stores.entity.type.CuisineType;
import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalTime;

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

    public Store(){ }

    @Builder
    public Store(String name, CuisineType type, String address, Double minOrderAmount, LocalTime opening,
                 LocalTime closing, Boolean isOpen) {

        this.name = name;
        this.type = type;
        this.address = address;
        this.minOrderAmount = minOrderAmount;
        this.opening = opening;
        this.closing = closing;
        this.isOpen = isOpen;
    }
}
