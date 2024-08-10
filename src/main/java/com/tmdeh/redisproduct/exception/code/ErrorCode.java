package com.tmdeh.redisproduct.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    TOKEN_NOTFOUND(HttpStatus.UNAUTHORIZED, "토큰을 찾을 수 었습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),

    MEMBER_NOTFOUND(HttpStatus.NOT_FOUND, "해당하는 유저가 없습니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료됐습니다."),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "지원하지 않는 토큰입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 토큰입니다."),

    ALREADY_EXIST_EMAIL(HttpStatus.BAD_REQUEST, "이미 사용중인 이메일입니다."),


    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "이메일과 비밀번호가 일치하지 않습니다."),

    NOT_FOUND_PRODUCT(HttpStatus.NOT_FOUND, "존재하지 않는 상품입니다."),

    OUT_OF_STOCK(HttpStatus.BAD_REQUEST, "재고가 부족합니다.")
    ;
    private final HttpStatus code;
    private final String message;
}


