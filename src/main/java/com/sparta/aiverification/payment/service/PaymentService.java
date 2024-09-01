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
import lombok.RequiredArgsConstructor;
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
//        if (user.getRole() == UserRoleEnum.CUSTOMER || user.getRole() == UserRoleEnum.OWNER)
//            throw new RestApiException(PaymentErrorCode.UNAUTHORIZED_USER);
        if (requestDto.getPaymentMethod() != PaymentMethod.CARD)
            throw new RestApiException(PaymentErrorCode.BAD_METHOD_PAYMENT);
        Orders orders = orderService.findById(requestDto.getOrderId());
        orders.updateOrderPaymentState(OrderPaymentState.COMPLETED);
        return PaymentResponseDto.Create.of(paymentRepository.save(
                Payment.builder()
                        .user(user)
                        .paymentMethod(requestDto.getPaymentMethod())
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

    public List<PaymentResponseDto.Get> getPayments(User user) {
        return paymentRepository.findByUserId(user.getId()).stream()
                .map(PaymentResponseDto.Get::of)
                .toList();
    }

    public Payment findById(UUID paymentId) {
        return paymentRepository.findById(paymentId).orElseThrow(
                () -> new RestApiException(PaymentErrorCode.NOT_FOUND_PAYMENT)
        );
    }
}
