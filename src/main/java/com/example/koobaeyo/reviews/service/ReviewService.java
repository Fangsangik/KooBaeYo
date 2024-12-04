package com.example.koobaeyo.reviews.service;

import com.example.koobaeyo.reviews.dto.FindReviewByRateDto;
import com.example.koobaeyo.reviews.dto.ReviewRequestDto;
import com.example.koobaeyo.reviews.dto.ReviewCreateResponseDto;
import com.example.koobaeyo.reviews.dto.ReviewResponseDto;
import com.example.koobaeyo.reviews.entity.Review;
import com.example.koobaeyo.reviews.repository.ReviewRepository;
import com.example.koobaeyo.stores.dto.StoreResponseDetailDto;
import com.example.koobaeyo.stores.service.StoreService;
import com.example.koobaeyo.user.dto.finduser.FindUserResponseDto;
import com.example.koobaeyo.user.entity.User;
import com.example.koobaeyo.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final OrderService orderService;
    private final StoreService storeService;


    public ReviewService(ReviewRepository reviewRepository, UserService userService, OrderService orderService, StoreService storeService) {
        this.reviewRepository = reviewRepository;
        this.userService = userService;
        this.orderService = orderService;
        this.storeService = storeService;
    }

    @Transactional
    public ReviewCreateResponseDto createReview(Long orderId, Long userId, ReviewRequestDto reviewRequestDto) {
        orderService.findOrder(reviewRequestDto.getOrderId());
        FindUserResponseDto user = userService.findUser(userId);
        StoreResponseDetailDto store = storeService.searchStoreDetail();

        if (reviewRequestDto.getRate() < 1 || reviewRequestDto.getRate() > 5) {
            throw new IllegalArgumentException("평점은 1~5 사이여야합니다.");
        }

        Review review = reviewRepository.save(
                Review.builder()
                        .store(order.getStoreId())
                        .order(order.toEntity())
                        .rate(reviewRequestDto.getRate())
                        .content(reviewRequestDto.getContent())
                        .build()
        );

        return new ReviewCreateResponseDto(review.getId());
    }

    @Transactional(readOnly = true)
    public Page<ReviewResponseDto> findReview(Long storeId, Long userId, int page, int size) {
        pageValidation(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Review> reviews = reviewRepository.findByStoreIdAndExcludeUser(storeId, userId, pageable);

        return reviews.map(review -> new ReviewResponseDto(
                review.getUser().getId(), review.getRate(), review.getContent()));
    }

    @Transactional(readOnly = true)
    public Page<ReviewResponseDto> findReviewByRate(Long storeId, FindReviewByRateDto findReviewByRateDto, int page, int size) {
        pageValidation(page, size);

        if (minRate < 3 || maxRate > 5) {
            throw new IllegalArgumentException("평점은 3~5 사이여야합니다.");
        }

        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"));
        Page<Review> reviews = reviewRepository.findByReviewRateAndStoreId(storeId, findReviewByRateDto.getMinRate(), findReviewByRateDto.getMaxRate(), pageable);

        return reviews.map(review -> new ReviewResponseDto(review.getUser().getId(), review.getRate(), review.getContent()));
    }

    private static void pageValidation(int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException("페이지는 0보다 커야합니다.");
        }

        if (size > 10) {
            throw new IllegalArgumentException("사이즈는 10보다 작아야합니다.");
        }
    }

}
