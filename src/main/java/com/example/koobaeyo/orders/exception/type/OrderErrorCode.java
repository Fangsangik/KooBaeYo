package com.example.koobaeyo.orders.exception.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OrderErrorCode {

    //최소 주문금액 이상으로 주문 했을때 에러코드
    ORDER_MINIMUM_NOT_MET(HttpStatus.BAD_REQUEST, "최소 주문 금액을 만족해야 주문할 수 있습니다."),
    //가게 오픈시간전 주문 했을때 에러코드
    STORE_NOT_OPEN(HttpStatus.BAD_REQUEST, "가게 오픈시간 전입니다."),
    //가게영업 시간 이후 주문 했을때 에러코드
    STORE_CLOSED(HttpStatus.BAD_REQUEST, "가게 영업시간이 끝났습니다."),
    //가게 폐업여부 확인
    STORE_NO_LONGER_OPERATING(HttpStatus.BAD_REQUEST, "폐업한 가게입니다."),
    //주문 내역을 찾을 수 없을때 에러코드
    NOT_FOUND_ORDER(HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다!"),
    //페이지 크기 에러코드
    PAGE_INPUT_EXCEPTION(HttpStatus.BAD_REQUEST, "페이지는 0 이상 10 이하여야 합니다."),
    //사용자, 메뉴, 가게를 찾을 수 없을때 에러코드
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "메뉴를 찾을 수 없습니다."),
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "가게를 찾을 수 없습니다."),
    //배달완료 했을때 에러코드
    ALREADY_DELIVERED(HttpStatus.BAD_REQUEST, "배달이 완료되었습니다."),
    INVALID_ORDER_STATUS(HttpStatus.BAD_REQUEST, "잘못된 주문상태입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
