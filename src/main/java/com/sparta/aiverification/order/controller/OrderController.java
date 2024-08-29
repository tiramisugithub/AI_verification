package com.sparta.aiverification.order.controller;

import com.sparta.aiverification.common.RestApiResponse;
import com.sparta.aiverification.order.dto.OrderRequestDto;
import com.sparta.aiverification.order.dto.OrderResponseDto;
import com.sparta.aiverification.order.service.OrderService;
import com.sparta.aiverification.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public RestApiResponse<OrderResponseDto.CreateResponseDto> createOrder(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody OrderRequestDto.CreateRequestDto requestDto) {
        return RestApiResponse.success(orderService.createOrder(userDetails.getUser(), requestDto));
    }

    @GetMapping("/{orderId}")
    public RestApiResponse<OrderResponseDto.GetResponseDto> getOrder(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable UUID orderId) {
        return RestApiResponse.success(orderService.getOrder(userDetails.getUser(), orderId));
    }
}
