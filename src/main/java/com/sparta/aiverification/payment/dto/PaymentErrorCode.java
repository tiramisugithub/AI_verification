package com.sparta.aiverification.payment.dto;

import com.sparta.aiverification.common.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PaymentErrorCode implements ErrorCode {

    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "This Role is No Permission"),
    NOT_FOUND_PAYMENT(HttpStatus.NOT_FOUND, "This Payment is not found"),
    BAD_METHOD_PAYMENT(HttpStatus.BAD_REQUEST, "Payment method can be only card"),
    BAD_REQUEST_PAYMENT(HttpStatus.BAD_REQUEST, "This Payment is not matched current user");
    private final HttpStatus httpStatus;
    private final String message;

}
