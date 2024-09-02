package com.sparta.aiverification.order.dto;

import com.sparta.aiverification.order.entity.Orders;
import com.sparta.aiverification.ordermenu.dto.OrderMenuResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrderResponseDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Create{

        private UUID orderId;

        private String details;

        public static Create of(Orders orders){
            return Create.builder()
                    .orderId(orders.getId())
                    .details(orders.getDetail())
                    .build();
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Get{

        private UUID orderId;

        private List<OrderMenuResponseDto.GetByOrder> menuList;

        private Integer totalPrice;

        private String details;
        public static Get of(Orders orders){
            return Get.builder()
                    .orderId(orders.getId())
                    .menuList(orders.getOrderMenuList().stream().map(OrderMenuResponseDto.GetByOrder::of).toList())
                    .totalPrice(orders.getTotalPrice())
                    .details(orders.getDetail())
                    .build();
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Update{

        private UUID orderId;

        private Integer totalPrice;

        private String details;
        public static Update of(Orders orders){
            return Update.builder()
                    .orderId(orders.getId())
                    .totalPrice(orders.getTotalPrice())
                    .details(orders.getDetail())
                    .build();
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Delete {

        private LocalDateTime deletedAt;

        private Long deletedBy;

        public static Delete of(Orders orders){
            return Delete.builder()
                    .deletedAt(orders.getDeletedAt())
                    .deletedBy(orders.getDeletedBy())
                    .build();
        }
    }
}
