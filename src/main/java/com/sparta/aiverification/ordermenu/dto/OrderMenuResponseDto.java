package com.sparta.aiverification.ordermenu.dto;

import com.sparta.aiverification.ordermenu.entity.OrderMenu;
import com.sparta.aiverification.ordermenu.entity.OrderMenuRedis;
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
    static public class GetByOrder {
        private UUID menuId;
        private String name;
        private Integer price;
        private String description;
        private Integer quantity;
        public static GetByOrder of(OrderMenu orderMenu){
            return GetByOrder.builder()
                    .menuId(orderMenu.getId())
                    .name(orderMenu.getMenu().getName())
                    .price(orderMenu.getMenu().getPrice())
                    .description(orderMenu.getMenu().getDescription())
                    .quantity(orderMenu.getQuantity())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static public class GetByOrderMenu {

        private String orderMenuId;
        private UUID menuId;
        private Integer quantity;

        public static GetByOrderMenu of(OrderMenuRedis orderMenuRedis){
            return GetByOrderMenu.builder()
                    .orderMenuId(orderMenuRedis.getId())
                    .menuId(orderMenuRedis.getMenuId())
                    .quantity(orderMenuRedis.getQuantity())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static public class Delete {

        private String orderMenuId;
        private UUID menuId;
        public static Delete of(OrderMenuRedis orderMenuRedis){
            return Delete.builder()
                    .orderMenuId(orderMenuRedis.getId())
                    .menuId(orderMenuRedis.getMenuId())
                    .build();
        }
    }

}
