package com.sparta.aiverification.payment.controller;

import com.sparta.aiverification.common.RestApiResponse;
import com.sparta.aiverification.payment.dto.PaymentRequestDto;
import com.sparta.aiverification.payment.dto.PaymentResponseDto;
import com.sparta.aiverification.payment.service.PaymentService;
import com.sparta.aiverification.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public RestApiResponse<PaymentResponseDto.Create> createPayment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody PaymentRequestDto.Create requestDto) {
        return RestApiResponse.success(paymentService.createPayment(userDetails.getUser(), requestDto));
    }

}
