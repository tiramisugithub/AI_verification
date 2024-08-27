package com.sparta.aiverification.common;

import lombok.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommonResponse<T> {

    // Message
    private static final String SUCCESS_MESSAGE = "success";
    private static final String FAIL_MESSAGE = "fail";
    private static final String ERROR_MESSAGE = "error";

    private T data;
    private String message;
    private boolean success;

    public static <T> CommonResponse<T> success(T data) {
        return CommonResponse.<T>builder()
                .message(SUCCESS_MESSAGE)
                .data(data)
                .success(true)
                .build();
    }

    public static CommonResponse<?> successWithNoContent() {
        return CommonResponse.builder()
                .message(SUCCESS_MESSAGE)
                .success(true)
                .build();
    }

    // Hibernate Validator에 의해 유효하지 않은 데이터로 인해 API 호출이 거부될때 반환
    public static CommonResponse<?> fail(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError error : allErrors) {
            if (error instanceof FieldError) {
                errors.put(((FieldError) error).getField(), error.getDefaultMessage());
            } else {
                errors.put(error.getObjectName(), error.getDefaultMessage());
            }
        }
        return CommonResponse.builder()
                .message(ERROR_MESSAGE)
                .data(errors)
                .success(false)
                .build();
    }

    public static CommonResponse<?> error(String message) {
        return CommonResponse.builder()
                .message(FAIL_MESSAGE)
                .success(false)
                .build();
    }

}
