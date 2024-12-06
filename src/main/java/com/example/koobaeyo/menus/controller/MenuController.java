package com.example.koobaeyo.menus.controller;

import com.example.koobaeyo.common.CommonResponse;
import com.example.koobaeyo.common.constants.Auth;
import com.example.koobaeyo.menus.dto.MenuRequestDto;
import com.example.koobaeyo.menus.dto.MenuResponseDto;
import com.example.koobaeyo.menus.service.MenuService;
import com.example.koobaeyo.user.entity.User;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    /**
     * 메뉴 생성
     * @param user
     * @param storeId
     * @param : {@link MenuRequestDto}
     * @return : {@link @ResponseEntity<CommonResponse>}
     */
    @PostMapping("/menus")
    public ResponseEntity<CommonResponse> createMenu(
            @SessionAttribute(Auth.LOGIN_USER) User user,
            @PathVariable Long storeId,
            @RequestBody @Valid MenuRequestDto requestDto
    ){
        MenuResponseDto responseDto = menuService.createMenu(user, storeId, requestDto);
        return new ResponseEntity<>(new CommonResponse<>("성공했습니다.", responseDto), HttpStatus.CREATED);
    }

    /**
     * 메뉴 수정
     * @param user
     * @param storeId
     * @param menuId
     * @param : {@link MenuRequestDto}
     * @return : {@link @ResponseEntity<CommonResponse>}
     */
    @PostMapping("/menus/{menuId}")
    public ResponseEntity<CommonResponse> updateMenu(
            @SessionAttribute(Auth.LOGIN_USER) User user,
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @RequestBody @Valid MenuRequestDto requestDto
    ) {
        MenuResponseDto responseDto = menuService.updateMenu(user, storeId, menuId, requestDto);
        return new ResponseEntity<>(new CommonResponse<>("성공했습니다.", responseDto), HttpStatus.OK);
    }

    /**
     * 메뉴 삭제
     * @param user
     * @param storeId
     * @param menuId
     * @return : {@link @ResponseEntity<CommonResponse>}
     */
    @DeleteMapping("/menus/{menuId}")
    public ResponseEntity<CommonResponse> deleteMenu(
        @SessionAttribute(Auth.LOGIN_USER) User user,
        @PathVariable Long storeId,
        @PathVariable Long menuId
    ){
        menuService.delete(user, storeId,  menuId);
        return  new ResponseEntity<>(new CommonResponse<>("정상적으로 삭제 되었습니다."), HttpStatus.OK);

    }
}
