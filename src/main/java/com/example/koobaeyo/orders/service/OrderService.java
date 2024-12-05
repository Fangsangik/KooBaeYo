package com.example.koobaeyo.orders.service;

import com.example.koobaeyo.menus.entity.Menu;
import com.example.koobaeyo.menus.repository.MenuRepository;
import com.example.koobaeyo.orders.dto.OrderListResponseDto;
import com.example.koobaeyo.orders.dto.OrderRequestDto;
import com.example.koobaeyo.orders.dto.OrderResponseDto;
import com.example.koobaeyo.orders.entity.Order;
import com.example.koobaeyo.orders.repository.OrderRepository;
import com.example.koobaeyo.orders.type.OrderStatus;
import com.example.koobaeyo.stores.entity.Store;
import com.example.koobaeyo.stores.repository.StoreRepository;
import com.example.koobaeyo.user.entity.User;
import com.example.koobaeyo.user.repository.UserRepository;
import com.example.koobaeyo.user.service.UserService;
import com.example.koobaeyo.user.type.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;


@Service
public class OrderService {

    private  final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final UserService userService;

    public OrderService(UserRepository userRepository, StoreRepository storeRepository, OrderRepository orderRepository, MenuRepository menuRepository, UserService userService) {
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
        this.orderRepository = orderRepository;
        this.menuRepository = menuRepository;
        this.userService = userService;
    }

    //주문 생성
    @Transactional
    public OrderResponseDto createOrder(Long userId, OrderRequestDto orderRequestDto) {
        //유저, 가게, 메뉴 정보 확인
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        //스토어
        Store store = storeRepository.findById(orderRequestDto.getStoreId()).orElseThrow(() -> new RuntimeException("Store Not Found"));
        //메뉴
        Menu menu = menuRepository.findById(orderRequestDto.getMenuId()).orElseThrow(() -> new RuntimeException("Menu Not Found"));

        //최소주문 금액 체크
        double totalPrice = orderRequestDto.getQuantity()* menu.getPrice();
        if(totalPrice < store.getMinOrderAmount()) {
            throw new RuntimeException("최소 주문 금액을 만족해야 주문할 수 있습니다.");
        }

        //오픈/마감시간
        LocalTime now = LocalTime.now();
        if (now.isBefore(store.getOpening()) || now.isAfter(store.getClosing())) {
            throw new RuntimeException("가게의 오픈~마감 시간 내에 주문할 수 있습니다.");
        }

        //주문 저장
        return new OrderResponseDto(orderRepository.save(orderRequestDto.toEntity(user, store, menu)).getId());
    }

    //주문 상태 변경
    @Transactional
    public OrderResponseDto updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다."));

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);

        return new OrderResponseDto(
                order.getId()
        );
    }


    //주문 목록 조회
    @Transactional(readOnly = true)
    public Page<OrderListResponseDto> getAllOrders(Long storeId, User user, int page, int size) {
        pageValidation(page, size);

        Pageable pageable = PageRequest.of(page, size);

        Page<Order> orders;

        //사장님 가게의 주문 조회
        if(isOwner(user)) {
            storeRepository.findById(storeId)
                    .orElseThrow(() -> new RuntimeException("가게 정보를 찾을 수 없습니다."));

            orders = orderRepository.findByStoreId(storeId, pageable);
        }
        //유저의 주문 조회
        else {
            orders = orderRepository.findByUserId(user.getId(), pageable);
        }


        return orders.map(order -> new OrderListResponseDto(
                order.getId(),
                order.getStore().getId(),
                order.getUser().getId(),
                order.getMenu().getId(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getOrderStatus())
        );
    }

    //주문 목록 단건 조회
    @Transactional(readOnly = true)
    public OrderListResponseDto getOrderById(Long orderId, Long storeId, User user, int page, int size) {
        pageValidation(page, size);

        Pageable pageable = PageRequest.of(page, size);

        Order order;

        // 사장님 가게 주문 목록 단건 조회
        if (isOwner(user)) {
            storeRepository.findById(storeId)
                    .orElseThrow(() -> new RuntimeException("가게 아이디를 찾을 수 없습니다."));

            order = orderRepository.findByStoreIdAndId(storeId, orderId)
                    .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다."));
        }
        // 유저 주문 목록 단건 조회
        else {
            order = orderRepository.findByUserIdAndId(user.getId(), orderId)
                    .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다."));
        }

        return new OrderListResponseDto(
                order.getId(),
                order.getStore().getId(),
                order.getUser().getId(),
                order.getMenu().getId(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getOrderStatus()
        );
    }

    public void pageValidation(int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException("페이지 번호는 0 이상이어야 합니다.");
        }
        if (size > 10) {
            throw new IllegalArgumentException("페이지 크기는 10보다 작아야 합니다.");
        }
    }

    private boolean isOwner(User user) {
        Role role = userService.findUser(user.getId()).getRole();

        //유저
        if(role != Role.ADMIN){
            return false;
        }
        //사장님
        else {
            return true;
        }
    }
}

