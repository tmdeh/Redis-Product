package com.tmdeh.redisproduct.exception;

import com.tmdeh.redisproduct.exception.code.ErrorCode;
import org.springframework.web.client.HttpStatusCodeException;

public class CustomException extends HttpStatusCodeException {
    public CustomException(ErrorCode errorCode) {
        super(errorCode.getCode(), errorCode.getMessage());
    }
}
