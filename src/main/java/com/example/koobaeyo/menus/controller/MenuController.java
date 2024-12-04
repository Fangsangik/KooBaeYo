package com.example.koobaeyo.menus.controller;

import com.example.koobaeyo.common.constants.Auth;
import com.example.koobaeyo.menus.dto.MenuRequestDto;
import com.example.koobaeyo.menus.dto.MenuResponseDto;
import com.example.koobaeyo.menus.entity.Menu;
import com.example.koobaeyo.menus.service.MenuService;
import com.example.koobaeyo.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/stores/{storeId}")
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/menus")
    public ResponseEntity<MenuResponseDto> createMenu(
            @SessionAttribute(Auth.LOGIN_USER) User user,
            @PathVariable Long storeId,
            @RequestBody @Valid MenuRequestDto requestDto
    ){

        MenuResponseDto menuResponseDto = menuService.createMenu(user, storeId, requestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(menuResponseDto);
    }

    @PostMapping("/menus/{menuId}")
    public ResponseEntity<MenuResponseDto> updateMenu(
            @SessionAttribute(Auth.LOGIN_USER) User user,
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @RequestBody @Valid MenuRequestDto requestDto
    ) {

//        log.info("storeId :{}", storeId);
//        log.info("menuId {}", menuId);
        MenuResponseDto menuResponseDto = menuService.updateMenu(user, storeId, menuId, requestDto);

        return ResponseEntity
                .ok()
                .body(menuResponseDto);
    }

    @DeleteMapping("/menus/{menuId}")
    public ResponseEntity<String> deleteMenu(
        @SessionAttribute(Auth.LOGIN_USER) User user,
        @PathVariable Long storeId,
        @PathVariable Long menuId
    ){
        menuService.delete(user, storeId,  menuId);
        return ResponseEntity.ok().body("정상적으로 삭제 되었습니다.");
    }
}
