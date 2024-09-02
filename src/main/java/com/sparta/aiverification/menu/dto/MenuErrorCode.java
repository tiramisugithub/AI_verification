package com.sparta.aiverification.menu.dto;

import com.sparta.aiverification.common.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum MenuErrorCode implements ErrorCode {

  UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "This Role is No Permission"),
  NOT_FOUND_MENU(HttpStatus.NOT_FOUND, "This Menu is not found"),
  BAD_REQUEST_MENU(HttpStatus.BAD_REQUEST, "This Menu is not matched current user");

  private final HttpStatus httpStatus;
  private final String message;

}
