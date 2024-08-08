package com.tmdeh.redisproduct.exception;

import com.tmdeh.redisproduct.model.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@ControllerAdvice
public class ExceptionAdviceController {

    // 예측하지 못한 에외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> defaultErrorHandler(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 에러가 발생했습니다."));
    }

    // 의도된 에외
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<?>> exceptionHandler(CustomException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ApiResponse.error(ex));
    }
    
    // 404 핸들링
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleNotFound(NoHandlerFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(HttpStatus.NOT_FOUND, "Not Found"));
    }
}
