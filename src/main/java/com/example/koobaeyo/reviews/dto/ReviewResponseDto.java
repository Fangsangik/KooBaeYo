package com.example.koobaeyo.reviews.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewResponseDto {
    private Long userId;
    private int rate;
    private String content;
    private LocalDateTime createdAt;

    public ReviewResponseDto(Long userId, int rate, String content, LocalDateTime createdAt) {
        this.userId = userId;
        this.rate = rate;
        this.content = content;
        this.createdAt = createdAt;
    }
}
