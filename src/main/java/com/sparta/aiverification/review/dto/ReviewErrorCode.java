package com.sparta.aiverification.review.dto;

import com.sparta.aiverification.common.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewErrorCode implements ErrorCode {
    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "This Role is No Permission");

    private final HttpStatus httpStatus;
    private final String message;

}
