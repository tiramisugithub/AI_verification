package com.sparta.aiverification.ordermenu.service;

import com.sparta.aiverification.common.RestApiException;
import com.sparta.aiverification.ordermenu.dto.OrderMenuRequestDto;
import com.sparta.aiverification.ordermenu.dto.OrderMenuResponseDto;
import com.sparta.aiverification.ordermenu.entity.OrderMenuErrorCode;
import com.sparta.aiverification.ordermenu.entity.OrderMenuRedis;
import com.sparta.aiverification.ordermenu.repository.OrderMenuRedisRepository;
import com.sparta.aiverification.user.entity.User;
import com.sparta.aiverification.user.enums.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderMenuService {

    private final OrderMenuRedisRepository orderMenuRedisRepository;

    public OrderMenuResponseDto.GetByOrderMenu createOrderMenu(User user, OrderMenuRequestDto.Create requestDto) {
        if (user.getRole() == UserRoleEnum.OWNER)
            throw new RestApiException(OrderMenuErrorCode.UNAUTHORIZED_USER);
        return OrderMenuResponseDto.GetByOrderMenu.of(
                orderMenuRedisRepository.save(
                        OrderMenuRedis.builder()
                                .userId(user.getId())
                                .menuId(requestDto.getMenuId())
                                .quantity(requestDto.getQuantity())
                                .build()
                ));
    }

    public OrderMenuResponseDto.GetByOrderMenu updateOrderMenu(User user, OrderMenuRequestDto.Update requestDto) {
        if(user.getRole() == UserRoleEnum.OWNER)
            throw new RestApiException(OrderMenuErrorCode.UNAUTHORIZED_USER);
        OrderMenuRedis orderMenuRedis = findById(requestDto.getOrderMenuId());
        if(!orderMenuRedis.getUserId().equals(user.getId()))
            throw new RestApiException(OrderMenuErrorCode.BAD_REQUEST_ORDER_MENU);
        orderMenuRedis.updateQuantity(requestDto.getQuantity());
        return OrderMenuResponseDto.GetByOrderMenu.of(orderMenuRedisRepository.save(orderMenuRedis));
    }

    public OrderMenuRedis findById(String orderMenuId){
        return orderMenuRedisRepository.findById(orderMenuId).orElseThrow(
                () -> new RestApiException(OrderMenuErrorCode.NOT_FOUND_ORDER_MENU));
    }
}
