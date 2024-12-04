//package com.example.koobaeyo.reviews.dto;
//
//import com.example.koobaeyo.orders.entity.Order;
//import com.example.koobaeyo.reviews.entity.Review;
//import com.example.koobaeyo.stores.entity.Store;
//import com.example.koobaeyo.user.entity.User;
//import jakarta.validation.constraints.Max;
//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotBlank;
//import lombok.Builder;
//import lombok.Getter;
//
//@Getter
//public class ReviewRequestDto {
//
//    @Min(1)
//    @Max(5)
//    private int rate;
//
//    @NotBlank(message = "리뷰 내용은 필수입니다.")
//    private String content;
//
//
//    public ReviewRequestDto(int rate, String content) {
//        this.rate = rate;
//        this.content = content;
//    }
//
//    public Review toEntity(Store store, Order order, User user) {
//        return Review.builder()
//                .store(store)
//                .order(order)
//                .user(user)
//                .rate(this.rate)
//                .content(this.content)
//                .build();
//    }
//}
