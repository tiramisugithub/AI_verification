package com.sparta.aiverification.payment.controller;

import com.sparta.aiverification.common.RestApiResponse;
import com.sparta.aiverification.payment.dto.PaymentRequestDto;
import com.sparta.aiverification.payment.dto.PaymentResponseDto;
import com.sparta.aiverification.payment.service.PaymentService;
import com.sparta.aiverification.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public RestApiResponse<PaymentResponseDto.Create> createPayment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody PaymentRequestDto.Create requestDto) {
        return RestApiResponse.success(paymentService.createPayment(userDetails.getUser(), requestDto));
    }

    @GetMapping("/{paymentId}")
    public RestApiResponse<PaymentResponseDto.Get> getPayment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable UUID paymentId) {
        return RestApiResponse.success(paymentService.getPayment(userDetails.getUser(), paymentId));
    }

    @GetMapping
    public RestApiResponse<List<PaymentResponseDto.Get>> getPayments(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return RestApiResponse.success(paymentService.getPayments(userDetails.getUser()));
    }

}
