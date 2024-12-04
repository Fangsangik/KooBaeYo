package com.example.koobaeyo.menus.repository;

import com.example.koobaeyo.menus.entity.Menu;
import com.example.koobaeyo.menus.exception.MenuBaseException;
import com.example.koobaeyo.menus.exception.type.MenuErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    default Menu findByIdOrElseThrow(Long menuId){
        return findById(menuId)
                .orElseThrow(()-> new MenuBaseException(MenuErrorCode.MENU_NOT_FOUND));
    };
}
