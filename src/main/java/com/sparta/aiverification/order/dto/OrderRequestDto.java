package com.sparta.aiverification.order.dto;

import com.sparta.aiverification.order.entity.OrderPaymentState;
import com.sparta.aiverification.order.entity.OrderType;
import com.sparta.aiverification.ordermenu.dto.OrderMenuRequestDto;
import com.sparta.aiverification.ordermenu.entity.OrderMenu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

public class OrderRequestDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Create{

        private UUID storeId;

        private OrderType orderType;

        private String deliveryAddress;

        private String detail;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Update {

        private UUID orderId;
        private String detail;

    }
}
