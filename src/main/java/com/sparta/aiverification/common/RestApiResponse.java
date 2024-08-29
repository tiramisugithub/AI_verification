package com.sparta.aiverification.common;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RestApiResponse<T> {

    private static final String SUCCESS_MESSAGE = "success";

    private T data;
    private String message;
    private boolean success;

    public static <T> RestApiResponse<T> success(T data) {
        return RestApiResponse.<T>builder()
                .message(SUCCESS_MESSAGE)
                .data(data)
                .success(true)
                .build();
    }

    public static RestApiResponse<?> successWithNoContent() {
        return RestApiResponse.builder()
                .message(SUCCESS_MESSAGE)
                .success(true)
                .build();
    }

}
