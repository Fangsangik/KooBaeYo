package com.example.koobaeyo.reviews.dto;

import lombok.Getter;

@Getter
public class ReviewCreateResponseDto {
    private Long id;

    public ReviewCreateResponseDto(Long id) {
        this.id = id;
    }

    public ReviewRequestDto reviewRequestDto() {
        return new ReviewRequestDto(id);
    }
}
