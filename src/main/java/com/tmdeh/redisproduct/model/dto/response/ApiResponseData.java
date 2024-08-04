package com.tmdeh.redisproduct.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmdeh.redisproduct.exception.CustomException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseData<T> {

    private final HttpStatusCode status;
    private final String message;
    private final T data;

    public static <T> ApiResponseData<T> ok() {
        return new ApiResponseData<>(HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), null);
    }

    public static <T> ApiResponseData<T> ok(T data) {
        return new ApiResponseData<>(HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), data);
    }

    public static <T> ApiResponseData<T> fail(HttpStatus status, String message) {
        return new ApiResponseData<>(status, message, null);
    }

    public static <T> ApiResponseData<T> fail(CustomException exception) {
        return new ApiResponseData<>(exception.getStatusCode(), exception.getMessage(), null);
    }

}
