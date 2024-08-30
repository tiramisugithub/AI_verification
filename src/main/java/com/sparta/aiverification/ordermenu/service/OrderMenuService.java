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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderMenuService {

    private final OrderMenuRedisRepository orderMenuRedisRepository;

    public OrderMenuResponseDto.SimpleResponseDto createOrderMenu(User user, OrderMenuRequestDto requestDto) {
        if (user.getRole() == UserRoleEnum.OWNER)
            throw new RestApiException(OrderMenuErrorCode.UNAUTHORIZED_USER);
        return OrderMenuResponseDto.SimpleResponseDto.of(
                orderMenuRedisRepository.save(
                        OrderMenuRedis.builder()
                                .userId(user.getId())
                                .menuId(requestDto.getMenuId())
                                .quantity(requestDto.getQuantity())
                                .build()
                ));
    }

}
