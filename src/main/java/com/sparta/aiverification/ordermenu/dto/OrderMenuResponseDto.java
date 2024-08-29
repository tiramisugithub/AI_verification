package com.sparta.aiverification.ordermenu.dto;

import com.sparta.aiverification.ordermenu.entity.OrderMenu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

public class OrderMenuResponseDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static public class GetResponseDto{
        private UUID menuId;
        private String name;
        private Integer price;
        private String description;
        private Integer quantity;
        public static GetResponseDto of(OrderMenu orderMenu){
            return GetResponseDto.builder()
                    .menuId(orderMenu.getId())
                    .name(orderMenu.getMenu().getName())
                    .price(orderMenu.getMenu().getPrice())
                    .description(orderMenu.getMenu().getDescription())
                    .quantity(orderMenu.getQuantity())
                    .build();
        }
    }
}
