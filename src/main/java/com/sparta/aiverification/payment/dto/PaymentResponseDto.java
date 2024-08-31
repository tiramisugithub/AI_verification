package com.sparta.aiverification.payment.dto;

import com.sparta.aiverification.payment.entity.Payment;
import com.sparta.aiverification.payment.entity.PaymentMethod;
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
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Get {

        private UUID paymentId;
        private Integer totalPrice;
        private PaymentMethod paymentMethod;

        public static Get of(Payment payment) {
            return Get.builder()
                    .paymentId(payment.getId())
                    .totalPrice(payment.getOrder().getTotalPrice())
                    .paymentMethod(payment.getPaymentMethod())
                    .build();
        }
    }

}
