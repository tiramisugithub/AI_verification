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

        private List<OrderMenuResponseDto.GetByOrder> menuList;

        private Integer totalPrice;

        private String details;
        public static GetResponseDto of(Order order){
            return GetResponseDto.builder()
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
    public static class UpdateResponseDto{

        private UUID orderId;

        private Integer totalPrice;

        private String details;
        public static UpdateResponseDto of(Order order){
            return UpdateResponseDto.builder()
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
    public static class DeleteResponseDto {

        private LocalDateTime deletedAt;

        private Long deletedBy;

        public static DeleteResponseDto of(Order order){
            return DeleteResponseDto.builder()
                    .deletedAt(order.getDeletedAt())
                    .deletedBy(order.getDeletedBy())
                    .build();
        }
    }
}
