package com.sparta.aiverification.ordermenu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

public class OrderMenuRequestDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static public class CreateRequestDto{

        private UUID menuId;

        private Integer quantity;

    }
}
