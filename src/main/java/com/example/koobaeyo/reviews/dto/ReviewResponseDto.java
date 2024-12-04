package com.example.koobaeyo.reviews.dto;

import lombok.Getter;

@Getter
public class ReviewResponseDto {
    private Long userId;
    private int rate;
    private String content;

    public ReviewResponseDto(Long userId, int rate, String content) {
        this.userId = userId;
        this.rate = rate;
        this.content = content;
    }
}
