package com.example.koobaeyo.stores.controller;

import com.example.koobaeyo.common.CommonResponse;
import com.example.koobaeyo.stores.service.StoreService;
import com.example.koobaeyo.user.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
public class StoreController {

    private final StoreService storeService;


    public StoreController(StoreService storeService){
        this.storeService = storeService;
    }

    //가게 생성
    //최대 3개, 사장님 권한을 가진 유저만 생성 가능, 폐업 상태가 아닌 가게 최대 3개 폐업상태면 여러개 ㄱㅊ
    @PostMapping
    public ResponseEntity<CommonResponse<StoreOpenResponseDto>> openStore(
            @SessionAttribute("loginUser") User user,
            @RequestBody StoreOpenRequestDto dto)
    {

        StoreOpenResponseDto responseDto = storeService.openStore(user, dto);

        return new ResponseEntity<>(new CommonResponse<StoreOpenResponseDto>("성공했습니다.",responseDto), HttpStatus.CREATED);
    }
}
