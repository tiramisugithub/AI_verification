package com.sparta.aiverification.ordermenu.controller;

import com.sparta.aiverification.common.RestApiResponse;
import com.sparta.aiverification.ordermenu.dto.OrderMenuRequestDto;
import com.sparta.aiverification.ordermenu.dto.OrderMenuResponseDto;
import com.sparta.aiverification.ordermenu.service.OrderMenuService;
import com.sparta.aiverification.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-menu")
@RequiredArgsConstructor
public class OrderMenuController {

    private final OrderMenuService orderMenuService;

    @PostMapping
    public RestApiResponse<OrderMenuResponseDto.GetByOrderMenu> createOrderMenu(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody OrderMenuRequestDto.Create requestDto) {
        return RestApiResponse.success(orderMenuService.createOrderMenu(userDetails.getUser(), requestDto));
    }

    @PatchMapping
    public RestApiResponse<OrderMenuResponseDto.GetByOrderMenu> updateOrderMenu(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody OrderMenuRequestDto.Update requestDto) {
        return RestApiResponse.success(orderMenuService.updateOrderMenu(userDetails.getUser(), requestDto));
    }
    // 해당 유저가 주문한 내역 전체 조회
    @GetMapping
    public RestApiResponse<List<OrderMenuResponseDto.GetByOrderMenu>> getOrderMenuListByUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return RestApiResponse.success(orderMenuService.getOrderMenuListByUser(userDetails.getUser()));
    }

}
