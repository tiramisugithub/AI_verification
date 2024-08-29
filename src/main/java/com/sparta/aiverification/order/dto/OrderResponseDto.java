package com.sparta.aiverification.order.dto;

import com.sparta.aiverification.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
