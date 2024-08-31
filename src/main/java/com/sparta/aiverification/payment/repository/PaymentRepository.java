package com.sparta.aiverification.payment.repository;

import com.sparta.aiverification.payment.dto.PaymentResponseDto;
import com.sparta.aiverification.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    List<Payment> findByUserId(Long id);
}
