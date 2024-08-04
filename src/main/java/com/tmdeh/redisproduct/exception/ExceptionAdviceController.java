package com.tmdeh.redisproduct.exception;

import com.tmdeh.redisproduct.model.dto.response.ApiResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionAdviceController {

    // 예측하지 못한 에외
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponseData<?>> defaultErrorHandler(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseData.fail(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 에러가 발생했습니다."));
    }

    // 의도된 에외
    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<ApiResponseData<?>> exceptionHandler(CustomException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ApiResponseData.fail(ex));
    }
}
