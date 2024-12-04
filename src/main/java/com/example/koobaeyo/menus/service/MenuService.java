package com.example.koobaeyo.menus.service;

import com.example.koobaeyo.menus.dto.MenuRequestDto;
import com.example.koobaeyo.menus.dto.MenuResponseDto;
import com.example.koobaeyo.menus.entity.Menu;
import com.example.koobaeyo.menus.repository.MenuRepository;
import com.example.koobaeyo.stores.entity.Store;
import com.example.koobaeyo.stores.exception.StoreBaseException;
import com.example.koobaeyo.stores.exception.code.StoreErrorCode;
import com.example.koobaeyo.stores.repository.StoreRepository;
import com.example.koobaeyo.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Slf4j
@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    public MenuService(MenuRepository menuRepository, StoreRepository storeRepository) {
        this.menuRepository = menuRepository;
        this.storeRepository = storeRepository;
    }

    public MenuResponseDto createMenu(User user, Long storeId, MenuRequestDto requestDto) {

        // 가게 조회
        Store findStore = storeRepository.findById(storeId).orElseThrow(
                ()-> new StoreBaseException(StoreErrorCode.NOT_FOUND_STORE));

        // 예외처리 -  가게 주인만 메뉴 생성 가능
        if(!isStoreOwner(user.getId(), findStore.getOwner().getId())) {
            // TODO: 예외 커스텀 처리
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "해당 가게 주인만 메뉴를 생성할 수 있습니다.");
        }

        // 예외처리 - 가게가 open 일 때만 메뉴 생성 가능
        if(!isStoreOpen(findStore.getIsOpen())){
            // TODO: 예외 커스텀 처리
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "가게가 '폐업'인 경우 메뉴를 생성할 수 없습니다.");

        }

        Menu menu = new Menu(findStore, requestDto.getName(), requestDto.getDescription(), requestDto.getPrice());
        Menu saveMenu = menuRepository.save(menu);
        return new MenuResponseDto(saveMenu.getId());
    }

    /**
     *
     * @param user
     * @param storeId
     * @param menuId
     * @param @link{MenuRequestDto}
     * @return
     */
    @Transactional
    public MenuResponseDto updateMenu(User user, Long storeId, Long menuId, MenuRequestDto requestDto) {

        // 가게 조회
        Store findStore = storeRepository.findById(storeId).orElseThrow(
                ()-> new StoreBaseException(StoreErrorCode.NOT_FOUND_STORE));

        // 해당 메뉴가 존재하지는 확인
        Menu findMenu = menuRepository.findByIdOrElseThrow(menuId);

        // 예외처리 -  가게 주인만 메뉴 수정 가능
        if(!isStoreOwner(user.getId(), findStore.getOwner().getId())) {
            // TODO: 예외 커스텀 처리
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "해당 가게 주인만 메뉴를 수정할 수 있습니다.");
        }

        // 예외처리 - 가게가 open 일 때만 메뉴 수정 가능
        if(!isStoreOpen(findStore.getIsOpen())){
            // TODO: 예외 커스텀 처리
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "가게가 '폐업'인 경우 메뉴를 수정할 수 없습니다.");

        }

        // 예외처리 - 동일한 가게에서의 요청인지 확인
        if(!isSameStore(storeId, findStore.getId())){
            // TODO: 예외 커스텀 처리
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "요청한 가게 ID가 일치하지 않습니다.");
        }

        findMenu.update(requestDto.getName(), requestDto.getDescription(), requestDto.getPrice());

        return new MenuResponseDto(findMenu.getId());
    }


    public void delete(User user, Long storeId, Long menuId) {

        // 가게 조회
        Store findStore = storeRepository.findById(storeId).orElseThrow(
                ()-> new StoreBaseException(StoreErrorCode.NOT_FOUND_STORE));

        // 해당 메뉴가 존재하지는 확인
        Menu findMenu = menuRepository.findByIdOrElseThrow(menuId);

        // 예외처리 -  가게 주인만 메뉴 생성 가능
        if(!isStoreOwner(user.getId(), findStore.getOwner().getId())) {
            // TODO: 예외 커스텀 처리
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "해당 가게 주인만 메뉴를 삭제할 수 있습니다.");
        }

        // 예외처리 - 가게가 open 일 때만 메뉴 삭제 가능
        if(!isStoreOpen(findStore.getIsOpen())){
            // TODO: 예외 커스텀 처리
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "가게가 '폐업'인 경우 메뉴를 삭제할 수 없습니다.");

        }

        // 동일한 가게에서의 요청인지 확인
        if(!isSameStore(storeId, findStore.getId())){
            // TODO: 예외 커스텀 처리
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "요청한 가게 ID가 일치하지 않습니다.");
        }

        menuRepository.deleteById(menuId);
    }

    public boolean isStoreOwner(Long userId, Long ownerId){
        return userId.equals(ownerId);
    }

    private boolean isStoreOpen(Boolean isOpen) {
        return isOpen == true;
    }

    private boolean isSameStore(Long reqeustStoreId, Long findStoreId ){
        return findStoreId.equals(reqeustStoreId);
    }
}
