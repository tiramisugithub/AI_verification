package com.sparta.aiverification.payment.service;

import com.sparta.aiverification.common.RestApiException;
import com.sparta.aiverification.order.entity.Order;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Order order = orderService.findById(requestDto.getOrderId());
        order.updateOrderPaymentState(OrderPaymentState.COMPLETED);
        return PaymentResponseDto.Create.of(paymentRepository.save(
                Payment.builder()
                        .user(user)
                        .paymentMethod(requestDto.getPaymentMethod())
                        .order(orderService.findById(requestDto.getOrderId()))
                        .build()
        ));
    }

    public PaymentResponseDto.GetByPaymentId getPayment(User user, UUID paymentId) {
        Payment payment = findById(paymentId);
        if (!payment.getUser().getId().equals(user.getId())) {
            throw new RestApiException(PaymentErrorCode.BAD_REQUEST_PAYMENT);
        }
        return PaymentResponseDto.GetByPaymentId.of(payment);
    }

    public Payment findById(UUID paymentId) {
        return paymentRepository.findById(paymentId).orElseThrow(
                () -> new RestApiException(PaymentErrorCode.NOT_FOUND_PAYMENT)
        );
    }
}
