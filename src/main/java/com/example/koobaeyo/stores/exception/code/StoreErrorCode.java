package com.example.koobaeyo.stores.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum StoreErrorCode {


        /**
         * Server
         */
        VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "유효성 검사 실패"),
        CONSTRAINT_VIOLATION(HttpStatus.CONFLICT, "제약 조건 위반"),
        INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류가 발생하였습니다."),

        /**
         * Common
         */
        NOT_FOUND_ENUM_CONSTANT(HttpStatus.NOT_FOUND, "열거형 상수값을 찾을 수 없습니다."),
        IS_NULL(HttpStatus.BAD_REQUEST, "NULL 값이 들어왔습니다."),
        COMMON_INVALID_PARAM(HttpStatus.BAD_REQUEST, "요청한 값이 올바르지 않습니다."),
        NO_SUCH_METHOD(HttpStatus.BAD_REQUEST, "메소드를 찾을 수 없습니다."),

    /**
     * Store
     */
     IS_NOT_OWNER(HttpStatus.FORBIDDEN, "사장 권한이 없습니다!"),
     CANNOT_OPEN_STORE(HttpStatus.FORBIDDEN, "가게를 열수 없습니다!"),
     CANNOT_REMODEL_OTHERS(HttpStatus.FORBIDDEN, "타인의 가게를 리모델링할 수 없습니다!"),
     CANNOT_CLOSEDOWN_OTHERS(HttpStatus.FORBIDDEN,"타인의 가게를 폐업할 수 없습니다!"),
     NOT_FOUND_STORE(HttpStatus.NOT_FOUND, "가게를 찾을 수 없습니다!");



    private final HttpStatus httpStatus;
    private final String message;
}
