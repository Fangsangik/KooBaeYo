package com.example.koobaeyo.reviews.repository;

import com.example.koobaeyo.reviews.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select o.status from Order o where o.id = :orderId")
    Review findOrderStatusByOrderId(@Param("orderId") Long orderId);

    @Query("select r from Review r where r.store.id = :storeId and r.user.id != :userId")
    Page<Review> findByStoreIdAndExcludeUser(@Param("storeId") Long storeId, @Param("userId") Long userId, Pageable pageable);

    @Query("select r from Review r where r.store.id = :storeId and r.rate between :minRate and :maxRate")
    Page<Review> findByReviewRateAndStoreId(@Param("storeId") Long storeId, @Param("minRate") int minRate, @Param("maxRate") int maxRate, Pageable pageable);
}
