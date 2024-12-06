package com.example.koobaeyo.user.entity;

import com.example.koobaeyo.common.BaseEntity;
import com.example.koobaeyo.user.type.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.time.LocalDateTime;

@Entity
@Getter
@Table
//@SQLDelete(sql = "UPDATE user SET deleted_at = localDateTime.now() WHERE  id = ?")
//@SQLRestriction("deleted_at IS NULL")
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

//    @ColumnDefault("NULL")
    private LocalDateTime deletedAt;



    public User(){}

    public User(String name, String email, String password, Role role, String number) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.number = number;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
