package com.sparta.aiverification.order.repository;

import com.sparta.aiverification.order.dto.OrderResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface OrderRepositoryCustom {

    Page<OrderResponseDto.Get> findAllByCondition(Long userId, UUID storeId, Pageable pageable);

}
