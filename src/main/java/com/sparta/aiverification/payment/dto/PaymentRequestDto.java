package com.sparta.aiverification.payment.dto;

import com.sparta.aiverification.payment.entity.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

public class PaymentRequestDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Create{

        private UUID orderId;
        private PaymentMethod paymentMethod;
    }
}
