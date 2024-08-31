package com.sparta.aiverification.payment.dto;

import com.sparta.aiverification.payment.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

public class PaymentResponseDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Create{

        private UUID paymentId;
        public static Create of(Payment payment){
            return Create.builder()
                    .paymentId(payment.getId())
                    .build();
        }
    }
}
