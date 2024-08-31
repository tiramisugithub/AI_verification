package com.sparta.aiverification.ordermenu.controller;

import com.sparta.aiverification.common.RestApiResponse;
import com.sparta.aiverification.ordermenu.dto.OrderMenuRequestDto;
import com.sparta.aiverification.ordermenu.dto.OrderMenuResponseDto;
import com.sparta.aiverification.ordermenu.service.OrderMenuService;
import com.sparta.aiverification.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
}
