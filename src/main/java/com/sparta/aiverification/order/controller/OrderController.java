package com.sparta.aiverification.order.controller;

import com.sparta.aiverification.common.RestApiResponse;
import com.sparta.aiverification.order.dto.OrderRequestDto;
import com.sparta.aiverification.order.dto.OrderResponseDto;
import com.sparta.aiverification.order.service.OrderService;
import com.sparta.aiverification.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public RestApiResponse<OrderResponseDto.Create> createOrder(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody OrderRequestDto.Create requestDto) {
        return RestApiResponse.success(orderService.createOrder(userDetails.getUser(), requestDto));
    }

    @GetMapping("/{orderId}")
    public RestApiResponse<OrderResponseDto.Get> getOrder(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable UUID orderId) {
        return RestApiResponse.success(orderService.getOrder(userDetails.getUser(), orderId));
    }

    // 주문 전체 조회
    @GetMapping
    public RestApiResponse<Page<OrderResponseDto.Get>> getOrders(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {
        return RestApiResponse.success(orderService.getOrders(pageable, userDetails.getUser()));
    }

    // 해당 유저에 대한 전체 조회
    @GetMapping("/user")
    public RestApiResponse<Page<OrderResponseDto.Get>> getOrdersByUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {
        return RestApiResponse.success(orderService.getOrdersByUser(userDetails.getUser(), pageable));
    }

    // 해당 가게에 대한 전체 조회
    @GetMapping("/store/{storeId}")
    public RestApiResponse<Page<OrderResponseDto.Get>> getOrdersByStore(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable UUID storeId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {
        return RestApiResponse.success(orderService.getOrdersByStore(userDetails.getUser(), storeId, pageable));
    }

    // 요구사항 수정
    @PatchMapping
    public RestApiResponse<OrderResponseDto.Update> updateOrder(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody OrderRequestDto.Update requestDto) {
        return RestApiResponse.success(orderService.updateOrder(userDetails.getUser(), requestDto));
    }

    @DeleteMapping("/{orderId}")
    public RestApiResponse<OrderResponseDto.Delete> deleteOrder(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable UUID orderId) {
        return RestApiResponse.success(orderService.deleteOrder(userDetails.getUser(), orderId));
    }

}