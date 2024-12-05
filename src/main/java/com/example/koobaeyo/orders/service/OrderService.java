package com.example.koobaeyo.orders.service;

import com.example.koobaeyo.menus.service.MenuService;
import com.example.koobaeyo.orders.dto.OrderListResponseDto;
import com.example.koobaeyo.orders.dto.OrderRequestDto;
import com.example.koobaeyo.orders.dto.OrderResponseDto;
import com.example.koobaeyo.orders.entity.Order;
import com.example.koobaeyo.orders.repository.OrderRepository;
import com.example.koobaeyo.orders.type.OrderStatus;
import com.example.koobaeyo.stores.service.StoreService;
import com.example.koobaeyo.user.dto.finduser.FindUserResponseDto;
import com.example.koobaeyo.user.entity.User;
import com.example.koobaeyo.user.repository.UserRepository;
import com.example.koobaeyo.user.service.UserService;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final StoreService storeService;
    private final MenuService menuService;

    public OrderService(OrderRepository orderRepository, UserService userService, StoreService storeService, MenuService menuService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.storeService = storeService;
        this.menuService = menuService;
    }

    //가게랑 메뉴해서 받아서 생성자 주입


    //주문 생성
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        //유저, 가게, 메뉴 정보 확인
        FindUserResponseDto findUser = userService.findUser(orderRequestDto.getUserId());
        //스토어
        //메뉴

        //주문 생성
        Order order = Order.builder()
                .user(user)
                .store(store)
                .menu(menu)
                .quantity(orderRequestDto.getQuantity())
                .totalPrice(orderRequestDto.getTotalPrice())
                .orderStatus(OrderStatus.ACCEPTED)
                .build();

        //주문 저장
        orderRepository.save(order);
        return new OrderResponseDto(order.getId());
    }

    //주문 상태 변경
    public OrderResponseDto updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다."));

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);

        return new OrderResponseDto(order.getId());
    }


    //주문 목록 조회
    public List<OrderListResponseDto> getAllOrders(Long storeId, Long userId) {
        List<Order> orders; //= orderRepository.findAll();

        //storeId, userId 모두 없는 경우
        if(storeId == null && userId == null) {
            throw new IllegalArgumentException(("아이디 값을 넣어주세요."))
        }
        //storeId만 있는 경우
        else if(storeId != null && userId == null) {
            storeService.findStore(storeId);
            orders = orderRepository.findByStoreId(storeId)
        }
        //userId만 있는 경우
        else if(storeId == null && userId != null) {
            userService.findUser(userId);
            orders = orderRepository.findByUserId(userId);
        }
        //storeId, userId 둘다 있는 경우
        else {
            throw new IllegalArgumentException(("아이디를 한개만 선택해주세요"))
        }

        //for문으로 command(option)+enter
        return orders.stream()
                .map(order -> new OrderListResponseDto(
                        order.getId(),
                        order.getStore().getId(), //아직 스토어랑 연결 암된
                        order.getUser().getId(),
                        order.getMenu().getId(), //아직 메뉴랑 연결 안됨
                        order.getQuantity(),
                        order.getOrderStatus(),
                        order.getTotalPrice()))
                .collec(Collectors.toList());
    }

    //주문 목록 단건 조회
    public OrderResponseDto getOrderById(Long orderId, Long storeId, Long userId) {
        Order order;

        //storeId, userId 모두 없는 경우
        if(storeId == null && userId == null) {
            throw new IllegalArgumentException(("아이디 값을 넣어주세요."))
        }
        //storeId만 있는 경우
        else if(storeId != null && userId == null) {
            storeService.findStore(storeId);
            order = orderRepository.findByStoreIdAndId(storeId, orderId)
        }
        //userId만 있는 경우
        else if(storeId == null && userId != null) {
            userService.findUser(userId);
            order = orderRepository.findByUserIdAndId(userId, orderId);
        }
        //storeId, userId 둘다 있는 경우
        else {
            throw new IllegalArgumentException(("아이디를 한개만 선택해주세요"))
        }
    }
}