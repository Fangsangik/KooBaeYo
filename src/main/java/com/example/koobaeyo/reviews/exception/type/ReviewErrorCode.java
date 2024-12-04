package com.example.koobaeyo.reviews.exception.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReviewErrorCode {

    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "리뷰를 찾을 수 없습니다."),
    REVIEW_ALREADY_EXISTS(HttpStatus.CONFLICT, "리뷰가 이미 존재합니다."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다."),

    ORDER_STATUS_NOT_DELIVERED(HttpStatus.BAD_REQUEST, "배달이 완료되지 않은 주문입니다."),

    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "가게를 찾을 수 없습니다."),

    INVALID_RATE(HttpStatus.BAD_REQUEST, "평점은 3점 이상 5점 이하로 입력해주세요."),
    INVALID_INPUT_RATE(HttpStatus.BAD_REQUEST, "평점은 1점 이상 5점 이하로 입력해주세요."),

    PAGE_INPUT_EXCEPTION(HttpStatus.BAD_REQUEST, "페이지는 0 이상 10 이하여야 합니다."),;

    private final HttpStatus httpStatus;
    private final String message;
}
