package com.example.koobaeyo.reviews.dto;

import lombok.Getter;

@Getter
public class FindReviewByRateDto {
    private int minRate;
    private int maxRate;

    public FindReviewByRateDto(int minRate, int maxRate) {
        this.minRate = minRate;
        this.maxRate = maxRate;
    }
}
