package com.sparta.aiverification.order.dto;

import com.sparta.aiverification.ordermenu.dto.OrderMenuRequestDto;
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
    public static class CreateRequestDto{

        private UUID storeId;

        private List<OrderMenuRequestDto.CreateRequestDto> menuList;

        private String detail;

    }

}
