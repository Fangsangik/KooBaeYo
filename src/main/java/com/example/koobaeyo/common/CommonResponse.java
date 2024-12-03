package com.example.koobaeyo.common;


import lombok.Getter;

/**
 * create on 2024. 11. 28. create by IntelliJ IDEA.
 *
 * <p> Response body에 담을 공통 객체. </p>
 * 모든 API를 통일된 형태로 응답하기 위해 생성했습니다.
 *
 * @author Seokgyu Hwang (Chris)
 * @version 1.0
 * @since 1.0
 */
@Getter
public class CommonResponse<T> {

    /**
     * Response 메세지.
     */
    private final String message;
    /**
     * Response 데이터.
     */
    private final T data;

    /**
     * 생성자.
     *
     * @param message Response 메세지
     * @param data    Response 데이터
     */
    public CommonResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    /**
     * 생성자. {@code data} 필드는 {@code null}로 초기화 합니다.
     *
     * @param message Response 메세지
     */
    public CommonResponse(String message) {
        this(message, null);
    }
}
