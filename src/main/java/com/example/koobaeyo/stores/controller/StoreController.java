package com.example.koobaeyo.stores.controller;

import com.example.koobaeyo.common.CommonResponse;
import com.example.koobaeyo.common.constants.Auth;
import com.example.koobaeyo.stores.dto.*;
import com.example.koobaeyo.stores.service.StoreService;
import com.example.koobaeyo.user.entity.User;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;


    public StoreController(StoreService storeService){
        this.storeService = storeService;
    }

    //가게 생성
    @PostMapping
    public ResponseEntity<CommonResponse<StoreOpenResponseDto>> openStore(
            @SessionAttribute(Auth.LOGIN_USER) User user,
           @Valid @RequestBody StoreOpenRequestDto dto)
    {
        StoreOpenResponseDto responseDto = storeService.openStore(user, dto);

        return new ResponseEntity<>(new CommonResponse<>("성공했습니다.", responseDto), HttpStatus.CREATED);
    }

    //가게이름으로 조회
    @GetMapping
    public ResponseEntity<CommonResponse<List<StoreResponseDto>>> searchStoreByName(
        @RequestParam String storeName
    ){
        List<StoreResponseDto> dtoList = storeService.searchStoreByName(storeName);

        return new ResponseEntity<>(new CommonResponse<>("성공했습니다.",dtoList),HttpStatus.OK);
    }

    //가게 단건 조회
    @GetMapping("/{storeId}")
    public ResponseEntity<CommonResponse<StoreResponseDetailDto>> searchStoreDetail(
            @PathVariable Long storeId
    ){
        StoreResponseDetailDto responseDto = storeService.searchStoreDetail(storeId);

        return new ResponseEntity<>(new CommonResponse<>("성공했습니다.",responseDto),HttpStatus.OK);
    }

    //가게 리모델링
    @PatchMapping("/{storeId}")
    public ResponseEntity<CommonResponse<StoreOpenResponseDto>> remodelingStore(
        @PathVariable Long storeId,
        @Valid @RequestBody StoreRemodelRequestDto dto,
        @SessionAttribute(Auth.LOGIN_USER) User user
    ){
        StoreOpenResponseDto responseDto = storeService.remodelingStore(storeId, dto, user);

        return new ResponseEntity<>(new CommonResponse<>("성공했습니다.",responseDto),HttpStatus.OK);
    }

    //가게 폐업
    @Transactional
    @DeleteMapping("/{storeId}")
    public ResponseEntity<CommonResponse<StoreOpenResponseDto>> closeDownStore(
        @PathVariable Long storeId,
        @SessionAttribute(Auth.LOGIN_USER) User user
    ){
        StoreOpenResponseDto responseDto = storeService.closeDownStore(storeId,user);

        return new ResponseEntity<>(new CommonResponse<>("성공했습니다.",responseDto),HttpStatus.OK);

    }

}
