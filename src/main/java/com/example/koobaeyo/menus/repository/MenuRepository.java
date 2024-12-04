package com.example.koobaeyo.menus.repository;

import com.example.koobaeyo.menus.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    default Menu findByIdOrElseThrow(Long menuId){
        return findById(menuId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 메뉴가 존재하지 않습니다."));
    };

}
