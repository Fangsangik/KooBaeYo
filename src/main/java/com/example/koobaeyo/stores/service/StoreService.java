package com.example.koobaeyo.stores.service;

import com.example.koobaeyo.stores.dto.*;
import com.example.koobaeyo.stores.entity.Store;
import com.example.koobaeyo.stores.exception.StoreBaseException;
import com.example.koobaeyo.stores.exception.code.StoreErrorCode;
import com.example.koobaeyo.stores.repository.StoreRepository;
import com.example.koobaeyo.user.entity.User;
import com.example.koobaeyo.user.service.UserService;
import com.example.koobaeyo.user.type.Role;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final UserService userService;


    public StoreService(StoreRepository storeRepository, UserService userService) {
        this.storeRepository = storeRepository;
        this.userService = userService;
    }

    //최대 3개, 사장님 권한을 가진 유저만 생성 가능, 폐업 상태가 아닌 가게 최대 3개 폐업상태면 여러개 ㄱㅊ
    //예외 403 FORBIDDEN
    public StoreOpenResponseDto openStore(User user, StoreOpenRequestDto dto) {

        checkOwner(user);

        if(storeRepository.countOpenStoresByOwner(user.getId()) >= 3){
            throw new StoreBaseException(StoreErrorCode.CANNOT_OPEN_STORE);
        }

        Store openedStore = storeRepository.save(dto.toEntity(user));

        return new StoreOpenResponseDto(openedStore);

    }


    //가게이름으로 가게를 검색(목록 조회)
    public List<StoreResponseDto> searchStoreByName(User user, String storeName) {

        List<Store> storeList =  isOwner(user) ? storeRepository.findAllByName(storeName) : storeRepository.findAllByNameIsOpen(storeName);

        return storeList.stream().map(StoreResponseDto::new).toList();
    }

    //가게 단건 조회시
    public StoreResponseDetailDto searchStoreDetail(User user, Long storeId) {
        Optional<Store> optionalStore = isOwner(user) ?
                storeRepository.findById(storeId)
                : storeRepository.findByIdIsOpen(storeId);

        if(optionalStore.isEmpty()){
            throw new StoreBaseException(StoreErrorCode.NOT_FOUND_STORE);
        }

        return new StoreResponseDetailDto(optionalStore.get());
    }

    //가게 리모델링
    //자신 가게만 리모델링 가능(스토어 아이디가 내가 오픈한 가게가 아니면)
    @Transactional
    public StoreOpenResponseDto remodelingStore(Long storeId, StoreRemodelRequestDto dto, User user) {
        checkOwner(user);
        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new StoreBaseException(StoreErrorCode.NOT_FOUND_STORE));

        if(!Objects.equals(store.getOwner().getId(), user.getId())){
            throw new StoreBaseException(StoreErrorCode.CANNOT_REMODEL_OTHERS);
        }

        //내가 사장님이면
        store.remodel(dto);

        return new StoreOpenResponseDto(store);
    }

    public StoreOpenResponseDto closeDownStore(Long storeId, User user) {
        checkOwner(user);

        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new StoreBaseException(StoreErrorCode.NOT_FOUND_STORE));

        if(!Objects.equals(store.getOwner().getId(), user.getId())){
            throw new StoreBaseException(StoreErrorCode.CANNOT_CLOSEDOWN_OTHERS);
        }

        //내가 사장님이면
        store.closeDown();

        return new StoreOpenResponseDto(store);
    }

    private void checkOwner(User user) {
        Role role = userService.findUser(user.getId()).getRole();

        if(role != Role.ADMIN){
            throw new StoreBaseException(StoreErrorCode.IS_NOT_OWNER);
        }
    }

    private boolean isOwner(User user) {
        Role role = userService.findUser(user.getId()).getRole();

        return role == Role.ADMIN;
    }
}
