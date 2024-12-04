package com.example.koobaeyo.orders.repository;

import com.example.koobaeyo.orders.entity.Order;
import com.example.koobaeyo.orders.type.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByStoreId(Long storeId);
    List<Order>findByUserId(Long userId);

    Optional<Order> findByStoreIdAndId(Long storeId, Long orderId);
    Optional<Order> findByUserIdAndId(Long userId, Long orderId);
}
