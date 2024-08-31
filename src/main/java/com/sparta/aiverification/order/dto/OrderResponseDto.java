package com.sparta.aiverification.order.dto;

import com.sparta.aiverification.order.entity.Order;
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

        public static Create of(Order order){
            return Create.builder()
                    .orderId(order.getId())
                    .details(order.getDetail())
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
        public static Get of(Order order){
            return Get.builder()
                    .orderId(order.getId())
                    .menuList(order.getOrderMenuList().stream().map(OrderMenuResponseDto.GetByOrder::of).toList())
                    .totalPrice(order.getTotalPrice())
                    .details(order.getDetail())
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
        public static Update of(Order order){
            return Update.builder()
                    .orderId(order.getId())
                    .totalPrice(order.getTotalPrice())
                    .details(order.getDetail())
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

        public static Delete of(Order order){
            return Delete.builder()
                    .deletedAt(order.getDeletedAt())
                    .deletedBy(order.getDeletedBy())
                    .build();
        }
    }
}
