package com.example.koobaeyo.orders.repository;

import com.example.koobaeyo.orders.entity.Order;
import com.example.koobaeyo.orders.type.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findByStoreId(Long storeId, Pageable pageable);
    Page<Order>findByUserId(Long userId, Pageable pageable);

    Optional<Order> findByStoreIdAndId(Long storeId, Long orderId);
    Optional<Order> findByUserIdAndId(Long userId, Long orderId);
}
