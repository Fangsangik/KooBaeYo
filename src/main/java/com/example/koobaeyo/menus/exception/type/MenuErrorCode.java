package com.example.koobaeyo.menus.exception.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MenuErrorCode {

    // 메뉴가 없는 경우
    MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 메뉴가 존재하지 않습니다."),

    // 메뉴 생성
    MENU_CREATION_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "해당 가게 주인만 메뉴를 생성할 수 있습니다."),
    MENU_CREATION_CLOSED_STORE(HttpStatus.BAD_REQUEST, "가게가 '폐업'인 경우 메뉴를 생성할 수 없습니다."),

    // 메뉴 수정
    MENU_UPDATE_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "해당 가게 주인만 메뉴를 수정할 수 있습니다."),
    MENU_UPDATE_CLOSED_STORE(HttpStatus.BAD_REQUEST, "가게가 '폐업'인 경우 메뉴를 수정할 수 없습니다."),
    MENU_UPDATE_INVALID_STORE_ID(HttpStatus.BAD_REQUEST, "요청한 가게 ID가 일치하지 않습니다."),

    // 메뉴 삭제
    MENU_DELETION_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "해당 가게 주인만 메뉴를 삭제할 수 있습니다."),
    MENU_DELETION_CLOSED_STORE(HttpStatus.BAD_REQUEST, "가게가 '폐업'인 경우 메뉴를 삭제할 수 없습니다."),
    MENU_DELETION_INVALID_STORE_ID(HttpStatus.BAD_REQUEST, "요청한 가게 ID가 일치하지 않습니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
