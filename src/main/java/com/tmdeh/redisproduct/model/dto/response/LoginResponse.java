package com.tmdeh.redisproduct.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

// TODO: @JsonInclude(JsonInclude.Include.NON_NULL) 삭제
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class LoginResponse {
    private String refreshToken;
    private String accessToken;
}
