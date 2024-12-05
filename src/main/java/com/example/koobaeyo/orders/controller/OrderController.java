package com.example.koobaeyo.orders.controller;


import com.example.koobaeyo.common.CommonResponse;
import com.example.koobaeyo.common.constants.Auth;
import com.example.koobaeyo.orders.dto.OrderListResponseDto;
import com.example.koobaeyo.orders.dto.OrderRequestDto;
import com.example.koobaeyo.orders.dto.OrderResponseDto;
import com.example.koobaeyo.orders.dto.OrderStatusUpdateDto;
import com.example.koobaeyo.orders.service.OrderService;
import com.example.koobaeyo.user.entity.User;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    //주문 생성
    @PostMapping
    public ResponseEntity<CommonResponse<OrderResponseDto>> createOrder(
            @SessionAttribute(Auth.LOGIN_USER) User user,
            @RequestBody OrderRequestDto orderRequestDto) {

        OrderResponseDto order = orderService.createOrder(user.getId(), orderRequestDto);
        return ResponseEntity.ok(new CommonResponse<>("주문이 완료되었습니다.", order));
    }

    //주문 상태 변경
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<CommonResponse<OrderResponseDto>> updateOrderStatus(
        @PathVariable Long orderId,
        @RequestBody @Valid OrderStatusUpdateDto orderStatusUpdateDto) {

        OrderResponseDto updatedOrder = orderService.updateOrderStatus(orderId, orderStatusUpdateDto.getOrderStatus());
        return ResponseEntity.ok(new CommonResponse<>("주문 상태가 업데이트되었습니다.", updatedOrder));
    }

    //주문 목록 조회
    @GetMapping
    public ResponseEntity<CommonResponse<Page<OrderListResponseDto>>> getAllOrders(
        @RequestParam(required = false) Long storeId,
        @SessionAttribute(Auth.LOGIN_USER) User user,
        @RequestParam(value = "page", required = false, defaultValue = "0") int page,
        @RequestParam(value = "size", required = false, defaultValue = "10") int size){

        return ResponseEntity.ok(new CommonResponse<>("주문 목록 조회 성공", orderService.getAllOrders(storeId, user, page, size)));
    }

    //주문 목록 단건 조회
    @GetMapping("/{orderId}")
    public ResponseEntity<CommonResponse<OrderListResponseDto>> getOrderById(
        @PathVariable Long orderId,
        @RequestParam(required = false) Long storeId,
        @SessionAttribute(Auth.LOGIN_USER) User user,
        @RequestParam(value = "page", required = false, defaultValue = "0") int page,
        @RequestParam(value = "size", required = false, defaultValue = "10") int size) {

        OrderListResponseDto orderListResponseDto = orderService.getOrderById(orderId, storeId, user, page, size);

        return ResponseEntity.ok(new CommonResponse<>("주문조회 성공", orderListResponseDto));
        }

}
