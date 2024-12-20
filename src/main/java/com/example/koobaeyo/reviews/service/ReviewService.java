package com.example.koobaeyo.reviews.service;

import com.example.koobaeyo.orders.entity.Order;
import com.example.koobaeyo.orders.repository.OrderRepository;
import com.example.koobaeyo.orders.type.OrderStatus;
import com.example.koobaeyo.reviews.dto.FindReviewByRateDto;
import com.example.koobaeyo.reviews.dto.ReviewRequestDto;
import com.example.koobaeyo.reviews.dto.ReviewCreateResponseDto;
import com.example.koobaeyo.reviews.dto.ReviewResponseDto;
import com.example.koobaeyo.reviews.entity.Review;
import com.example.koobaeyo.reviews.exception.ReviewBaseException;
import com.example.koobaeyo.reviews.exception.type.ReviewErrorCode;
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

import static com.example.koobaeyo.reviews.exception.type.ReviewErrorCode.*;

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

    /**
     * Review 생성 서비스
     * 1. 사용자 조회
     * 2. 주문 조회
     * 3. 가게 조회
     * 4. 리뷰 생성
     *
     * @param orderId
     * @param userId
     * @param reviewRequestDto
     * @return ReviewCreateResponseDto
     */
    @Transactional
    public ReviewCreateResponseDto createReview(Long orderId, Long userId, ReviewRequestDto reviewRequestDto) {
        // 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ReviewBaseException(USER_NOT_FOUND));

        // 주문 조회
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ReviewBaseException(ORDER_NOT_FOUND));

        validateReviewCreation(orderId, order);

        // 가게 조회
        Store store = order.getStore();
        if (store == null) {
            throw new ReviewBaseException(STORE_NOT_FOUND);
        }

        Review save = reviewRepository.save(reviewRequestDto.toEntity(store, order ,user));
        return new ReviewCreateResponseDto(save);
    }

    /**
     * Review 조회 서비스
     *
     * @param storeId
     * @param userId
     * @param page
     * @param size
     * @return Page<ReviewResponseDto>
     */
    @Transactional(readOnly = true)
    public Page<ReviewResponseDto> findReview(Long storeId, Long userId, int page, int size) {
        pageValidation(page);

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Review> reviews = reviewRepository.findByStoreIdAndExcludeUser(storeId, userId, pageable);

        return reviews.map(review -> new ReviewResponseDto(
                review.getUser().getId(), review.getRate(), review.getContent(), review.getCreatedAt()));
    }

    /**
     * Review 평점 조회 서비스
     *
     * @param storeId
     * @param findReviewByRateDto
     * @param page
     * @param size
     * @return Page<ReviewResponseDto>
     */
    @Transactional(readOnly = true)
    public Page<ReviewResponseDto> findReviewByRate(Long storeId, FindReviewByRateDto findReviewByRateDto, int page, int size) {
        pageValidation(page);

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Review> reviews = reviewRepository.findByReviewRateAndStoreId(storeId, findReviewByRateDto.getMinRate(), findReviewByRateDto.getMaxRate(), pageable);

        return reviews.map(review -> new ReviewResponseDto(review.getUser().getId(), review.getRate(), review.getContent(), review.getCreatedAt()));
    }

    private static void pageValidation(int page) {
        if (page < 0) {
            throw new ReviewBaseException(PAGE_INPUT_EXCEPTION);
        }
    }

    private void validateReviewCreation(Long orderId, Order order) {
        if (order.getOrderStatus() != OrderStatus.DELIVERED) {
            throw new ReviewBaseException(ORDER_STATUS_NOT_DELIVERED);
        }

        if (reviewRepository.existsByOrderId(orderId)) {
            throw new ReviewBaseException(REVIEW_ALREADY_EXISTS);
        }
    }
}
