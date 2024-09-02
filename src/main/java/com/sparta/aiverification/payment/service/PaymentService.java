package com.sparta.aiverification.payment.service;

import com.sparta.aiverification.common.RestApiException;
import com.sparta.aiverification.order.entity.Orders;
import com.sparta.aiverification.order.entity.OrderPaymentState;
import com.sparta.aiverification.order.service.OrderService;
import com.sparta.aiverification.payment.dto.PaymentErrorCode;
import com.sparta.aiverification.payment.dto.PaymentRequestDto;
import com.sparta.aiverification.payment.dto.PaymentResponseDto;
import com.sparta.aiverification.payment.entity.Payment;
import com.sparta.aiverification.payment.entity.PaymentMethod;
import com.sparta.aiverification.payment.repository.PaymentRepository;
import com.sparta.aiverification.user.entity.User;
import com.sparta.aiverification.user.enums.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final OrderService orderService;

    @Transactional
    public PaymentResponseDto.Create createPayment(User user, PaymentRequestDto.Create requestDto) {
        if (user.getRole() != UserRoleEnum.CUSTOMER)
            throw new RestApiException(PaymentErrorCode.UNAUTHORIZED_USER);
        if (requestDto.getPaymentMethod() != PaymentMethod.CARD)
            throw new RestApiException(PaymentErrorCode.BAD_METHOD_PAYMENT);
        Orders orders = orderService.findById(requestDto.getOrderId());
        orders.updateOrderPaymentState(OrderPaymentState.COMPLETED);
        return PaymentResponseDto.Create.of(paymentRepository.save(
                Payment.builder()
                        .user(orders.getUser())
                        .paymentMethod(requestDto.getPaymentMethod())
                        .isDeleted(false)
                        .orders(orderService.findById(requestDto.getOrderId()))
                        .build()
        ));
    }

    public PaymentResponseDto.Get getPayment(User user, UUID paymentId) {
        Payment payment = findById(paymentId);
        if (!payment.getUser().getId().equals(user.getId())) {
            throw new RestApiException(PaymentErrorCode.BAD_REQUEST_PAYMENT);
        }
        return PaymentResponseDto.Get.of(payment);
    }

    public Page<PaymentResponseDto.Get> getPayments(User user, Pageable pageable) {
        if (user.getRole() == UserRoleEnum.MASTER || user.getRole() == UserRoleEnum.MANAGER) {
            return paymentRepository.findByCondition(null, pageable);
        } else {
            return paymentRepository.findByCondition(user.getId(), pageable);
        }
    }

    public Payment findById(UUID paymentId) {
        return paymentRepository.findById(paymentId).orElseThrow(
                () -> new RestApiException(PaymentErrorCode.NOT_FOUND_PAYMENT)
        );
    }
}
