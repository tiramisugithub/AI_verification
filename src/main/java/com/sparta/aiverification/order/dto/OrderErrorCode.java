package com.sparta.aiverification.order.dto;

import com.sparta.aiverification.common.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode {

    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "This Role is No Permission"),
    NOT_FOUND_ORDER(HttpStatus.NOT_FOUND, "This Order is not found"),
    BAD_REQUEST_ORDER(HttpStatus.BAD_REQUEST, "This Order is not matched current user");

    private final HttpStatus httpStatus;
    private final String message;

}
