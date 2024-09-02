package com.sparta.aiverification.payment.repository;

import com.sparta.aiverification.payment.dto.PaymentResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentRepositoryCustom {

    Page<PaymentResponseDto.Get> findByCondition(Long userId, Pageable pageable);
}
