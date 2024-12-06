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


    /**
     * 가게 오픈 서비스(오픈한 가게가 3개 이상이면 오픈 x)
     * @param user 로그인된 유저
     * @param dto 가게오픈에 필요한 정보 {@link StoreOpenRequestDto}
     * @return 오픈된 가게의 아이디 {@link StoreIdResponseDto}
     * @throws StoreBaseException 가게예외
     */
    public StoreIdResponseDto openStore(User user, StoreOpenRequestDto dto) {

        checkOwner(user);

        if(storeRepository.countOpenStoresByOwner(user.getId()) >= 3){
            throw new StoreBaseException(StoreErrorCode.CANNOT_OPEN_STORE);
        }

        Store openedStore = storeRepository.save(dto.toEntity(user));

        return new StoreIdResponseDto(openedStore);

    }


    /**
     * 가게 이름 조회 서비스
     * @param user 로그인된 유저
     * @param storeName 가게 아이디
     * @return 사장님이면 모든 가게목록, 유저면 오픈된 가게목록 {@link StoreResponseDto}
     */
    public List<StoreResponseDto> searchStoreByName(User user, String storeName) {

        List<Store> storeList =  isOwner(user) ? storeRepository.findAllByNameUsingOwner(storeName,user)
                : storeRepository.findAllByNameIsOpen(storeName);

        return storeList.stream().map(StoreResponseDto::new).toList();
    }


    /**
     * 가게 단건 상세 조회 서비스
     * @param user 로그인된 유저
     * @param storeId 가게 아이디
     * @return 가게 상세 조회, 폐업된 가게면 사장님만 조회 {@link StoreResponseDetailDto}
     */
    public StoreResponseDetailDto searchStoreDetail(User user, Long storeId) {
        Optional<Store> optionalStore = isOwner(user) ?
                storeRepository.findByIdUsingOwner(storeId,user)
                : storeRepository.findByIdIsOpen(storeId);

        if(optionalStore.isEmpty()){
            throw new StoreBaseException(StoreErrorCode.NOT_FOUND_STORE);
        }

        return new StoreResponseDetailDto(optionalStore.get());
    }

    /**
     * 가게 리모델링 서비스
     * @param storeId 가게 아이디
     * @param dto 가게 리모델링 정보 {@link StoreRemodelRequestDto}
     * @param user 로그인된 유저
     * @return 리모델링된 가게 아이디 {@link StoreIdResponseDto}
     */
    @Transactional
    public StoreIdResponseDto remodelingStore(Long storeId, StoreRemodelRequestDto dto, User user) {
        checkOwner(user);
        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new StoreBaseException(StoreErrorCode.NOT_FOUND_STORE));

        if(!Objects.equals(store.getOwner().getId(), user.getId())){
            throw new StoreBaseException(StoreErrorCode.CANNOT_REMODEL_OTHERS);
        }

        //내가 사장님이면
        store.remodel(dto);

        return new StoreIdResponseDto(store);
    }

    /**
     * 가게 폐업 서비스
     * @param storeId 가게 아이디
     * @param user 로그인된 유저
     * @return 폐업된 가게 아이디 {@link StoreIdResponseDto}
     */
    public StoreIdResponseDto closeDownStore(Long storeId, User user) {
        checkOwner(user);

        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new StoreBaseException(StoreErrorCode.NOT_FOUND_STORE));

        if(!Objects.equals(store.getOwner().getId(), user.getId())){
            throw new StoreBaseException(StoreErrorCode.CANNOT_CLOSEDOWN_OTHERS);
        }

        //내가 사장님이면
        store.closeDown();

        return new StoreIdResponseDto(store);
    }

    /**
     * 사장님 권한 인증(아니면 예외던짐)
     * @param user 로그인된 유저
     * @throws StoreBaseException 가게예외
     */
    private void checkOwner(User user) {
        Role role = userService.findUser(user.getId()).getRole();

        if(role != Role.ADMIN){
            throw new StoreBaseException(StoreErrorCode.IS_NOT_OWNER);
        }
    }

    /**
     * 사장님 권한 여부
     * @param user 로그인된 유저
     * @return 사장님이면 true, 일반 유저면 false
     */
    private boolean isOwner(User user) {
        Role role = userService.findUser(user.getId()).getRole();

        return role == Role.ADMIN;
    }
}
