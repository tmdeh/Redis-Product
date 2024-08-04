package com.tmdeh.redisproduct.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JwtDto {
    private final String accessToken;
    private final String refreshToken;
}
