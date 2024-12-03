package com.example.koobaeyo.user.entity;

import com.example.koobaeyo.common.BaseEntity;
import com.example.koobaeyo.user.type.Role;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private Boolean isDeleted = false;


    public User(){}

    public User(String name, String email, String password, Role role, String number) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.number = number;
    }


}
