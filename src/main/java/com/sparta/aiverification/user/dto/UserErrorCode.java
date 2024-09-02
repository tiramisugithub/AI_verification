package com.sparta.aiverification.user.dto;

import com.sparta.aiverification.common.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "This Role is No Permission"),
    DUPLICATE_USERNAME(HttpStatus.BAD_REQUEST, "중복된 사용자가 존재합니다."),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "중복된 Email 입니다."),
    INVALID_ROLE(HttpStatus.FORBIDDEN, "관리자 및 매니저는 등록이 불가능합니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
