package com.sparta.aiverification.ordermenu.entity;

import com.sparta.aiverification.common.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderMenuErrorCode implements ErrorCode {

    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "This Role is No Permission"),
    NOT_FOUND_ORDER_MENU(HttpStatus.NOT_FOUND, "This Menu is not found"),
    BAD_REQUEST_ORDER_MENU(HttpStatus.BAD_REQUEST, "This OrderMenu is not matched user");


    private final HttpStatus httpStatus;
    private final String message;
}
