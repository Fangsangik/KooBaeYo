package com.example.koobaeyo.reviews.dto;

import com.example.koobaeyo.reviews.entity.Review;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewRequestDto {
    private Long id;
    private int rate;
    private String content;


    public ReviewRequestDto(int rate, String content) {
        this.rate = rate;
        this.content = content;
    }

    public ReviewRequestDto(Long id) {
        this.id = id;
    }

    public static Review toEntity(ReviewRequestDto reviewRequestDto) {
        return Review.builder()
                .rate(reviewRequestDto.rate)
                .content(reviewRequestDto.content)
                .build();
    }
}
