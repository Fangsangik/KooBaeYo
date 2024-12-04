package com.example.koobaeyo.reviews.service;

import com.example.koobaeyo.orders.entity.Order;
import com.example.koobaeyo.orders.repository.OrderRepository;
import com.example.koobaeyo.orders.type.OrderStatus;
import com.example.koobaeyo.reviews.dto.FindReviewByRateDto;
import com.example.koobaeyo.reviews.dto.ReviewRequestDto;
import com.example.koobaeyo.reviews.dto.ReviewCreateResponseDto;
import com.example.koobaeyo.reviews.dto.ReviewResponseDto;
import com.example.koobaeyo.reviews.entity.Review;
import com.example.koobaeyo.reviews.repository.ReviewRepository;
import com.example.koobaeyo.stores.entity.Store;
import com.example.koobaeyo.stores.repository.StoreRepository;
import com.example.koobaeyo.user.entity.User;
import com.example.koobaeyo.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;

    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository, OrderRepository orderRepository, StoreRepository storeRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.storeRepository = storeRepository;
    }


    @Transactional
    public ReviewCreateResponseDto createReview(Long orderId, Long userId, ReviewRequestDto reviewRequestDto) {
        // 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 주문 조회
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        // 가게 조회
        Store store = order.getStore();
        if (store == null) {
            throw new IllegalArgumentException("가게를 찾을 수 없습니다.");
        }

        if (reviewRequestDto.getRate() < 1 || reviewRequestDto.getRate() > 5) {
            throw new IllegalArgumentException("평점은 1~5 사이여야 합니다.");
        }


        // 리뷰 저장
        Review review = reviewRepository.save(
                Review.builder()
                        .store(store)
                        .order(order)
                        .user(user) // User 추가
                        .rate(reviewRequestDto.getRate())
                        .content(reviewRequestDto.getContent())
                        .build()
        );

        // 응답 반환
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

        if (findReviewByRateDto.getMinRate() < 3 || findReviewByRateDto.getMaxRate() > 5 || findReviewByRateDto.getMinRate() > findReviewByRateDto.getMaxRate()) {
            throw new IllegalArgumentException("평점은 3~5 사이여야합니다.");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
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
