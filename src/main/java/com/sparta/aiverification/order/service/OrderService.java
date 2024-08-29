package com.sparta.aiverification.order.service;

import com.sparta.aiverification.common.RestApiException;
import com.sparta.aiverification.order.dto.OrderErrorCode;
import com.sparta.aiverification.order.dto.OrderRequestDto;
import com.sparta.aiverification.order.dto.OrderResponseDto;
import com.sparta.aiverification.order.entity.Order;
import com.sparta.aiverification.order.repository.OrderRepository;
import com.sparta.aiverification.ordermenu.dto.OrderMenuRequestDto;
import com.sparta.aiverification.ordermenu.entity.OrderMenu;
import com.sparta.aiverification.tmp.entity.Menu;
import com.sparta.aiverification.tmp.entity.Store;
import com.sparta.aiverification.user.entity.User;
import com.sparta.aiverification.tmp.service.MenuService;
import com.sparta.aiverification.tmp.service.StoreService;
import com.sparta.aiverification.user.enums.UserRoleEnum;
import com.sparta.aiverification.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final StoreService storeService;

    private final UserService userService;

    private final MenuService menuService;

    private final OrderRepository orderRepository;


    @Transactional
    public OrderResponseDto.CreateResponseDto createOrder(User user, OrderRequestDto.CreateRequestDto requestDto) {
        if(user.getRole() != UserRoleEnum.CUSTOMER)
            throw new RestApiException(OrderErrorCode.UNAUTHORIZED_USER);

        Order order = createEmptyOrder(userService.findById(user.getId())
                , storeService.findById(requestDto.getStoreId())
                , requestDto.getDetail()
        );
        Integer totalPrice = 0;
        for(OrderMenuRequestDto.CreateRequestDto createRequestDto : requestDto.getMenuList()){
            Menu menu = menuService.findById(createRequestDto.getMenuId());
            totalPrice += menu.getPrice();
            order.addOrderMenu(OrderMenu.builder()
                    .order(order)
                    .menu(menu)
                    .quantity(createRequestDto.getQuantity())
                    .build());
        }
        order.updateTotalPrice(totalPrice);
        return OrderResponseDto.CreateResponseDto.of(orderRepository.save(order));
    }

    private Order createEmptyOrder(User user, Store store, String detail) {
        List<OrderMenu> orderMenuList = new ArrayList<>();
        return Order.builder()
                .user(user)
                .store(store)
                .orderMenuList(orderMenuList)
                .detail(detail)
                .build();
    }
}
