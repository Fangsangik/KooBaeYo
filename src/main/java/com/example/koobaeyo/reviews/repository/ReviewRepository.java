package com.example.koobaeyo.reviews.repository;

import com.example.koobaeyo.reviews.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ReviewRepository extends JpaRepository<Review, Long> {

    boolean existsByOrderId(Long orderId);

    /**
     * 가게별 리뷰 조회 단 본인이 작성한 리뷰는 제외
     * @param storeId
     * @param userId
     * @param pageable
     */
    @Query("select r from Review r where r.store.id = :storeId and r.user.id != :userId")
    Page<Review> findByStoreIdAndExcludeUser(@Param("storeId") Long storeId, @Param("userId") Long userId, Pageable pageable);

    /**
     * 가게별 리뷰 조회
     * @param storeId
     * @param pageable
     */
    @Query("select r from Review r where r.store.id = :storeId and r.rate between :minRate and :maxRate")
    Page<Review> findByReviewRateAndStoreId(@Param("storeId") Long storeId, @Param("minRate") int minRate, @Param("maxRate") int maxRate, Pageable pageable);
}
