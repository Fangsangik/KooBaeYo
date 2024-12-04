package com.example.koobaeyo.reviews.controller;

import com.example.koobaeyo.reviews.dto.ReviewCreateResponseDto;
import com.example.koobaeyo.reviews.dto.ReviewRequestDto;
import com.example.koobaeyo.reviews.dto.ReviewResponseDto;
import com.example.koobaeyo.reviews.service.ReviewService;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/reviews")
    public ResponseEntity<ReviewCreateResponseDto> createReview
            (@RequestBody ReviewRequestDto reviewRequestDto) {
        ReviewCreateResponseDto reviewCreateResponseDto = reviewService.createReview(reviewRequestDto);
        return ResponseEntity.ok(reviewCreateResponseDto);
    }

    @GetMapping("/stores/{storeId}/reviews")
    public ResponseEntity<Page<ReviewResponseDto>> getReviews
            (@SessionAttribute("loginUser") Long userId,
             @PathVariable Long storeId,
             @RequestParam(value = "page", required = false, defaultValue = "0") int page,
             @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        Page<ReviewResponseDto> reviewResponseDto = reviewService.findReview(storeId, userId, page, size);
        return ResponseEntity.ok(reviewResponseDto);
    }

    @GetMapping("/stores/{storeId}/reviews/rate")
    public ResponseEntity<Page<ReviewResponseDto>> getReviewRate
            (@PathVariable Long storeId,
             @RequestParam int minRate,
             @RequestParam int maxRate,
             @RequestParam(value = "page", required = false, defaultValue = "0") int page,
             @RequestParam(value = "size", required = false, defaultValue = "10") int size) {

        Page<ReviewResponseDto> reviewResponseDto = reviewService.findReviewByRate(storeId, minRate, maxRate, page, size);
        return ResponseEntity.ok(reviewResponseDto);
    }
}
