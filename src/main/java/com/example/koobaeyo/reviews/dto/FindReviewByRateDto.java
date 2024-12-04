package com.example.koobaeyo.reviews.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
public class FindReviewByRateDto {
    @Min(3)
    @Max(5)
    private int minRate;

    @Min(3)
    @Max(5)
    private int maxRate;

    @AssertTrue(message = "minRate는 maxRate 보다 작아야 합니다")
    public boolean isValidateRange() {
        return minRate < maxRate;
    }

    public FindReviewByRateDto(int minRate, int maxRate) {
        this.minRate = minRate;
        this.maxRate = maxRate;
    }
}
