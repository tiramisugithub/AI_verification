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

import java.util.List;
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

    // 주문 전체 조회
    @GetMapping
    public RestApiResponse<List<OrderResponseDto.GetResponseDto>> getOrders(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return RestApiResponse.success(orderService.getOrders(userDetails.getUser()));
    }

    // 해당 유저에 대한 전체 조회
    @GetMapping("/user")
    public RestApiResponse<List<OrderResponseDto.GetResponseDto>> getOrdersByUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return RestApiResponse.success(orderService.getOrdersByUser(userDetails.getUser()));
    }

    // 해당 가게에 대한 전체 조회
    @GetMapping("/store/{storeId}")
    public RestApiResponse<List<OrderResponseDto.GetResponseDto>> getOrdersByStore(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable UUID storeId) {
        return RestApiResponse.success(orderService.getOrdersByStore(userDetails.getUser(), storeId));
    }

    // 요구사항 수정
    @PatchMapping
    public RestApiResponse<OrderResponseDto.UpdateResponseDto> updateOrder(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody OrderRequestDto.UpdateRequestDto requestDto) {
        return RestApiResponse.success(orderService.updateOrder(userDetails.getUser(), requestDto));
    }

    @DeleteMapping("/{orderId}")
    public RestApiResponse<OrderResponseDto.DeleteResponseDto> deleteOrder(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable UUID orderId) {
        return RestApiResponse.success(orderService.deleteOrder(userDetails.getUser(), orderId));
    }

}
