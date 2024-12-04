package com.example.koobaeyo.reviews.dto;

import com.example.koobaeyo.reviews.entity.Review;
import lombok.Getter;

@Getter
public class ReviewCreateResponseDto {
    private Long id;

    public ReviewCreateResponseDto(Review review) {
        this.id = review.getId();
    }
}
