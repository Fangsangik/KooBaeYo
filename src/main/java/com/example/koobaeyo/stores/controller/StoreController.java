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

    /**
     * 가게 오픈
     * @param user 로그인된 유저
     * @param dto 가게오픈에 필요한 정보 {@link StoreOpenRequestDto}
     * @return 오픈된 가게의 아이디 {@link StoreIdResponseDto}
     */
    @PostMapping
    public ResponseEntity<CommonResponse<StoreIdResponseDto>> openStore(
            @SessionAttribute(Auth.LOGIN_USER) User user,
           @Valid @RequestBody StoreOpenRequestDto dto)
    {
        StoreIdResponseDto responseDto = storeService.openStore(user, dto);

        return new ResponseEntity<>(new CommonResponse<>("성공했습니다.", responseDto), HttpStatus.CREATED);
    }


    /**
     * 가게이름 가게목록 조회
     * @param storeName 가게이름
     * @param user 로그인된 유저
     * @return 사장님이면 모든 가게목록, 유저면 오픈된 가게목록 {@link StoreResponseDto}
     */
    @GetMapping
    public ResponseEntity<CommonResponse<List<StoreResponseDto>>> searchStoreByName(
        @RequestParam String storeName,
        @SessionAttribute(Auth.LOGIN_USER) User user
    ){
        List<StoreResponseDto> dtoList = storeService.searchStoreByName(user,storeName);

        return new ResponseEntity<>(new CommonResponse<>("성공했습니다.",dtoList),HttpStatus.OK);
    }


    /**
     * 가게 단건 조회
     * @param storeId 가게아이디
     * @param user 로그인된 유저
     * @return 가게 상세 조회, 폐업된 가게면 사장님만 조회 {@link StoreResponseDetailDto}
     */
    @GetMapping("/{storeId}")
    public ResponseEntity<CommonResponse<StoreResponseDetailDto>> searchStoreDetail(
            @PathVariable Long storeId,
            @SessionAttribute(Auth.LOGIN_USER) User user
    ){
        StoreResponseDetailDto responseDto = storeService.searchStoreDetail(user,storeId);

        return new ResponseEntity<>(new CommonResponse<>("성공했습니다.",responseDto),HttpStatus.OK);
    }


    /**
     * 가게 리모델링
     * @param storeId 가게아이디
     * @param dto 가게 리모델링 정보 {@link StoreRemodelRequestDto}
     * @param user 로그인된 유저
     * @return 리모델링된 가게 아이디 {@link StoreIdResponseDto}
     */
    @PatchMapping("/{storeId}")
    public ResponseEntity<CommonResponse<StoreIdResponseDto>> remodelingStore(
        @PathVariable Long storeId,
        @Valid @RequestBody StoreRemodelRequestDto dto,
        @SessionAttribute(Auth.LOGIN_USER) User user
    ){
        StoreIdResponseDto responseDto = storeService.remodelingStore(storeId, dto, user);

        return new ResponseEntity<>(new CommonResponse<>("성공했습니다.",responseDto),HttpStatus.OK);
    }


    /**
     * 가게 폐업
     * @param storeId 가게아이디
     * @param user 로그인된 유저
     * @return 폐업된 가게 아이디 {@link StoreIdResponseDto}
     */
    @Transactional
    @DeleteMapping("/{storeId}")
    public ResponseEntity<CommonResponse<StoreIdResponseDto>> closeDownStore(
        @PathVariable Long storeId,
        @SessionAttribute(Auth.LOGIN_USER) User user
    ){
        StoreIdResponseDto responseDto = storeService.closeDownStore(storeId,user);

        return new ResponseEntity<>(new CommonResponse<>("성공했습니다.",responseDto),HttpStatus.OK);

    }

}
