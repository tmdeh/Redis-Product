package com.tmdeh.redisproduct.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmdeh.redisproduct.exception.CustomException;
import com.tmdeh.redisproduct.exception.code.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse <T> {

    private final Integer status;
    private final String message;
    private final T data;

    public static <T> ApiResponse<T> of(HttpStatus status, String message, T data) {
        return ApiResponse.<T>builder()
                .status(status.value())
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success() {
        return ApiResponse.<T>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> error(HttpStatus status, String message) {
        return ApiResponse.<T>builder()
                .status(status.value())
                .message(message)
                .build();
    }

    public static <T> ApiResponse<T> error(CustomException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        return ApiResponse.<T>builder()
                .status(errorCode.getCode().value())
                .message(errorCode.getMessage())
                .build();
    }

}
