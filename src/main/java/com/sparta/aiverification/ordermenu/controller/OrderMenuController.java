package com.sparta.aiverification.ordermenu.controller;

import com.sparta.aiverification.common.RestApiResponse;
import com.sparta.aiverification.ordermenu.dto.OrderMenuRequestDto;
import com.sparta.aiverification.ordermenu.dto.OrderMenuResponseDto;
import com.sparta.aiverification.ordermenu.service.OrderMenuService;
import com.sparta.aiverification.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order-menu")
@RequiredArgsConstructor
public class OrderMenuController {

    private final OrderMenuService orderMenuService;

    @PostMapping
    public RestApiResponse<OrderMenuResponseDto.SimpleResponseDto> createOrderMenu(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody OrderMenuRequestDto requestDto) {
        return RestApiResponse.success(orderMenuService.createOrderMenu(userDetails.getUser(), requestDto));
    }
}
