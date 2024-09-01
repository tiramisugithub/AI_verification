package com.sparta.aiverification.order.service;

import com.sparta.aiverification.common.RestApiException;
import com.sparta.aiverification.menu.entity.Menu;
import com.sparta.aiverification.menu.service.MenuService;
import com.sparta.aiverification.order.dto.OrderErrorCode;
import com.sparta.aiverification.order.dto.OrderRequestDto;
import com.sparta.aiverification.order.dto.OrderResponseDto;
import com.sparta.aiverification.order.entity.Order;
import com.sparta.aiverification.order.entity.OrderPaymentState;
import com.sparta.aiverification.order.entity.OrderType;
import com.sparta.aiverification.order.repository.OrderRepository;
import com.sparta.aiverification.ordermenu.entity.OrderMenu;
import com.sparta.aiverification.ordermenu.entity.OrderMenuRedis;
import com.sparta.aiverification.ordermenu.service.OrderMenuService;
import com.sparta.aiverification.store.entity.Store;
import com.sparta.aiverification.store.service.StoreService;
import com.sparta.aiverification.user.entity.User;
import com.sparta.aiverification.user.enums.UserRoleEnum;
import com.sparta.aiverification.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final StoreService storeService;

    private final UserService userService;

    private final MenuService menuService;

    private final OrderRepository orderRepository;

    private final OrderMenuService orderMenuService;



    @Transactional
    public OrderResponseDto.Create createOrder(User user, OrderRequestDto.Create requestDto) {
        if(user.getRole() != UserRoleEnum.CUSTOMER)
            throw new RestApiException(OrderErrorCode.UNAUTHORIZED_USER);

        Order order = createEmptyOrder(userService.findById(user.getId())
            , storeService.findById(requestDto.getStoreId())
            , requestDto.getDetail()
            , requestDto.getOrderType()
            , requestDto.getDeliveryAddress()
        );
        List<OrderMenuRedis> orderMenuRedisList = orderMenuService.getOrderMenuListByUser(user.getId());
        int totalPrice = 0;
        for(OrderMenuRedis orderMenuRedis : orderMenuRedisList){
            Menu menu = menuService.findById(orderMenuRedis.getMenuId());
            totalPrice += menu.getPrice() * orderMenuRedis.getQuantity();
            order.addOrderMenu(OrderMenu.builder()
                .order(order)
                .menu(menu)
                .quantity(orderMenuRedis.getQuantity())
                .build());
        }
        order.updateTotalPrice(totalPrice);
        orderMenuService.deleteOrderMenuListInRedis(orderMenuRedisList);
        return OrderResponseDto.Create.of(orderRepository.save(order));
    }


    public OrderResponseDto.Get getOrder(User user, UUID orderId) {
        Order order = findById(orderId);
        if(!order.getUser().getId().equals(user.getId())){
            throw new RestApiException(OrderErrorCode.BAD_REQUEST_ORDER);
        }
        return OrderResponseDto.Get.of(order);
    }


    public List<OrderResponseDto.Get> getOrders(User user) {
        if(user.getRole() == UserRoleEnum.CUSTOMER || user.getRole() == UserRoleEnum.OWNER)
            throw new RestApiException(OrderErrorCode.UNAUTHORIZED_USER);
        return orderRepository.findAll()
            .stream()
            .map(OrderResponseDto.Get::of)
            .toList();
    }


    public List<OrderResponseDto.Get> getOrdersByUser(User user) {
        if(user.getRole() == UserRoleEnum.OWNER)
            throw new RestApiException(OrderErrorCode.UNAUTHORIZED_USER);
        return orderRepository.findAllByUserId(user.getId())
            .stream()
            .map(OrderResponseDto.Get::of)
            .toList();
    }


    public List<OrderResponseDto.Get> getOrdersByStore(User user, UUID storeId) {
        if(user.getRole() == UserRoleEnum.CUSTOMER)
            throw new RestApiException(OrderErrorCode.UNAUTHORIZED_USER);
        // User가 가지고 있는 Store인지 확인하는 로직 필요
        return orderRepository.findAllByStoreId(storeId)
            .stream()
            .map(OrderResponseDto.Get::of)
            .toList();
    }


    @Transactional
    public OrderResponseDto.Update updateOrder(User user
        ,OrderRequestDto.Update requestDto) {
        if(user.getRole() != UserRoleEnum.CUSTOMER)
            throw new RestApiException(OrderErrorCode.UNAUTHORIZED_USER);
        Order order = findById(requestDto.getOrderId());
        if(!user.getId().equals(order.getUser().getId()))
            throw new RestApiException(OrderErrorCode.BAD_REQUEST_ORDER);
        order.updateDetail(requestDto.getDetail());
        return OrderResponseDto.Update.of(order);
    }

    @Transactional
    public OrderResponseDto.Delete deleteOrder(User user, UUID orderId) {
        Order order = findById(orderId);
        if(user.getRole() == UserRoleEnum.OWNER){
            throw new RestApiException(OrderErrorCode.UNAUTHORIZED_USER);
        }
        if(!order.getUser().getId().equals(user.getId())){
            throw new RestApiException(OrderErrorCode.UNAUTHORIZED_USER);
        }
        order.deleteOrder(user.getId());
        return OrderResponseDto.Delete.of(order);
    }

    public Order findById(UUID orderId){
        return orderRepository.findById(orderId).orElseThrow(() -> new RestApiException(OrderErrorCode.NOT_FOUND_ORDER));
    }

    private Order createEmptyOrder(User user,
        Store store,
        String detail,
        OrderType orderType,
        String deliveryAddress) {
        List<OrderMenu> orderMenuList = new ArrayList<>();
        return Order.builder()
            .user(user)
            .store(store)
            .orderMenuList(orderMenuList)
            .detail(detail)
            .orderType(orderType)
            .deliveryAddress(deliveryAddress)
            .orderPaymentState(OrderPaymentState.PENDING)
            .isDeleted(false)
            .build();
    }

}