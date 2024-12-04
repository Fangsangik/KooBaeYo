package com.example.koobaeyo.orders.controller;


import com.example.koobaeyo.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping

    @PutMapping

    @PatchMapping

    @GetMapping

}
