package com.example.koobaeyo.orders.service;

import com.example.koobaeyo.menus.entity.Menu;
import com.example.koobaeyo.menus.repository.MenuRepository;
import com.example.koobaeyo.orders.dto.OrderListResponseDto;
import com.example.koobaeyo.orders.dto.OrderRequestDto;
import com.example.koobaeyo.orders.dto.OrderResponseDto;
import com.example.koobaeyo.orders.dto.OrderUpdateResponseDto;
import com.example.koobaeyo.orders.entity.Order;
import com.example.koobaeyo.orders.exception.OrderBaseException;
import com.example.koobaeyo.orders.exception.type.OrderErrorCode;
import com.example.koobaeyo.orders.repository.OrderRepository;
import com.example.koobaeyo.orders.type.OrderStatus;
import com.example.koobaeyo.stores.entity.Store;
import com.example.koobaeyo.stores.exception.StoreBaseException;
import com.example.koobaeyo.stores.exception.code.StoreErrorCode;
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

import static com.example.koobaeyo.reviews.exception.type.ReviewErrorCode.PAGE_INPUT_EXCEPTION;


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
        User user = userRepository.findById(userId).orElseThrow(() -> new OrderBaseException(OrderErrorCode.USER_NOT_FOUND));
        //스토어
        Store store = storeRepository.findById(orderRequestDto.getStoreId()).orElseThrow(() -> new OrderBaseException(OrderErrorCode.STORE_NOT_FOUND));

       //폐업 여부 확인
        if(store.getIsOpen() == false) {
            throw new OrderBaseException(OrderErrorCode.STORE_NO_LONGER_OPERATING);
        }

        //메뉴
        Menu menu = menuRepository.findById(orderRequestDto.getMenuId()).orElseThrow(() -> new OrderBaseException(OrderErrorCode.MENU_NOT_FOUND));

        //최소주문 금액 체크
        double totalPrice = orderRequestDto.getQuantity()* menu.getPrice();
        if(totalPrice < store.getMinOrderAmount()) {
            throw new OrderBaseException(OrderErrorCode.ORDER_MINIMUM_NOT_MET);
        }

        //오픈/마감시간
        LocalTime now = LocalTime.now();
        if (now.isBefore(store.getOpening())) {
            throw new OrderBaseException(OrderErrorCode.STORE_NOT_OPEN);
        } else if (now.isAfter(store.getClosing())) {
            throw new OrderBaseException(OrderErrorCode.STORE_CLOSED);
        }

        //주문 저장
        return new OrderResponseDto(orderRepository.save(orderRequestDto.toEntity(user, store, menu)).getId());
    }

    //주문 상태 변경
    @Transactional
    public OrderUpdateResponseDto updateOrderStatus(Long userId, Long orderId, OrderStatus orderStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderBaseException(OrderErrorCode.NOT_FOUND_ORDER));

        checkOwner(userRepository.findById(userId).orElseThrow(() -> new OrderBaseException(OrderErrorCode.USER_NOT_FOUND)));

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);

        return new OrderUpdateResponseDto(
                order.getId(),
                order.getOrderStatus()
        );
    }

    //주문 목록 조회
    @Transactional(readOnly = true)
    public Page<OrderListResponseDto> getAllOrders(Long storeId, User user, int page, int size) {
        pageValidation(page, size);

        Pageable pageable = PageRequest.of(page, size);

        Page<Order> orders;

        //사장님 가게의 주문 조회
        if(isOwner(user, storeId)) {
            //애랑
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
    public OrderListResponseDto getOrderById(Long orderId, Long storeId, User user) {

        Order order;

        // 사장님 가게 주문 목록 단건 조회
        if (isOwner(user, storeId)) {
            order = orderRepository.findByStoreIdAndId(storeId, orderId)
                    .orElseThrow(() -> new OrderBaseException(OrderErrorCode.NOT_FOUND_ORDER));
        }
        // 유저 주문 목록 단건 조회
        else {
            order = orderRepository.findByUserIdAndId(user.getId(), orderId)
                    .orElseThrow(() -> new OrderBaseException(OrderErrorCode.NOT_FOUND_ORDER));
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
            throw new OrderBaseException(OrderErrorCode.PAGE_INPUT_EXCEPTION);
        }
        if (size > 10) {
            throw new OrderBaseException(OrderErrorCode.PAGE_INPUT_EXCEPTION);
        }
    }

    private boolean isOwner(User user, Long storeId) {
        Role role = userService.findUser(user.getId()).getRole();
        //admin이 아니면 유저
        if(role != Role.ADMIN){
            return false;
        }
        //사장님
        else {
            return storeRepository.existsByIdAndOwnerId(storeId, user.getId());
        }
    }

    private void checkOwner(User user) {
        Role role = userService.findUser(user.getId()).getRole();

        if(role != Role.ADMIN){
            throw new StoreBaseException(StoreErrorCode.IS_NOT_OWNER);
        }
    }
}

