package com.example.koobaeyo.reviews.controller;

import com.example.koobaeyo.common.CommonResponse;
import com.example.koobaeyo.common.constants.Auth;
import com.example.koobaeyo.reviews.dto.FindReviewByRateDto;
import com.example.koobaeyo.reviews.dto.ReviewCreateResponseDto;
import com.example.koobaeyo.reviews.dto.ReviewRequestDto;
import com.example.koobaeyo.reviews.dto.ReviewResponseDto;
import com.example.koobaeyo.reviews.service.ReviewService;
import com.example.koobaeyo.user.entity.User;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    /**
     * 리뷰 생성
     * @param orderId
     * @param reviewRequestDto
     * @return ReviewCreateResponseDto
     */
    @PostMapping("/reviews/orders/{orderId}")
    public ResponseEntity<CommonResponse<ReviewCreateResponseDto>> createReview
            (@PathVariable Long orderId,
             @SessionAttribute(Auth.LOGIN_USER) User user,
             @Valid @RequestBody ReviewRequestDto reviewRequestDto) {
        ReviewCreateResponseDto reviewCreateResponseDto = reviewService.createReview(orderId, user.getId(), reviewRequestDto);
        return ResponseEntity.ok(new CommonResponse<>("리뷰 생성 성공", reviewCreateResponseDto));
    }

    /**
     * 리뷰 조회
     * @param storeId
     * @param page
     * @param size
     * @return Page<ReviewResponseDto>
     */
    @GetMapping("/stores/{storeId}/reviews")
    public ResponseEntity<CommonResponse<Page<ReviewResponseDto>>> getReviews
            (@PathVariable Long storeId,
             @SessionAttribute(Auth.LOGIN_USER) User user,
             @RequestParam(value = "page", required = false, defaultValue = "0") int page,
             @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        Page<ReviewResponseDto> reviewResponseDto = reviewService.findReview(storeId, user.getId(), page, size);
        return ResponseEntity.ok(new CommonResponse<>("리뷰 조회 성공", reviewResponseDto));
    }

    /**
     * 리뷰 평점 조회
     * @param storeId
     * @param findReviewByRateDto
     * @param page
     * @param size
     * @return Page<ReviewResponseDto>
     */
    @GetMapping("/stores/{storeId}/reviews/rate")
    public ResponseEntity<CommonResponse<Page<ReviewResponseDto>>> getReviewRate
            (@PathVariable Long storeId,
             @Valid @RequestBody FindReviewByRateDto findReviewByRateDto,
             @RequestParam(value = "page", required = false, defaultValue = "0") int page,
             @RequestParam(value = "size", required = false, defaultValue = "10") int size) {

        Page<ReviewResponseDto> reviewResponseDto = reviewService.findReviewByRate(storeId, findReviewByRateDto, page, size);
        return ResponseEntity.ok(new CommonResponse<>("리뷰 조회 성공", reviewResponseDto));
    }
}
