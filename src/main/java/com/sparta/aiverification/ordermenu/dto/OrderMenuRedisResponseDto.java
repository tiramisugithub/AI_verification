package com.sparta.aiverification.ordermenu.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Or;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderMenuRedisResponseDto {

    private List<OrderMenuResponseDto.SimpleResponseDto> orderMenuList;
}
