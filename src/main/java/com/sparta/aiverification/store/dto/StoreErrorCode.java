package com.sparta.aiverification.store.dto;

import com.sparta.aiverification.common.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum StoreErrorCode implements ErrorCode {

  UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "This Role is No Permission"),
  NOT_FOUND_STORE(HttpStatus.NOT_FOUND, "This Store is not found"),
  BAD_REQUEST_STORE(HttpStatus.BAD_REQUEST, "This Store is not matched current user");

  private final HttpStatus httpStatus;
  private final String message;

}
