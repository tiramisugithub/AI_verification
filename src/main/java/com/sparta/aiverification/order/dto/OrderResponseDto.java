package com.sparta.aiverification.order.dto;

import com.sparta.aiverification.order.entity.Order;
import com.sparta.aiverification.ordermenu.dto.OrderMenuResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

public class OrderResponseDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateResponseDto{

        private UUID orderId;

        private String details;

        public static CreateResponseDto of(Order order){
            return CreateResponseDto.builder()
                    .orderId(order.getId())
                    .details(order.getDetail())
                    .build();
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetResponseDto{

        private UUID orderId;

        private List<OrderMenuResponseDto.GetResponseDto> menuList;

        private Integer totalPrice;

        private String details;
        public static GetResponseDto of(Order order){
            return GetResponseDto.builder()
                    .orderId(order.getId())
                    .menuList(order.getOrderMenuList().stream().map(OrderMenuResponseDto.GetResponseDto::of).toList())
                    .totalPrice(order.getTotalPrice())
                    .details(order.getDetail())
                    .build();
        }
    }
}
