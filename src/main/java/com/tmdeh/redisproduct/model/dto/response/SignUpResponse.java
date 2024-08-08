package com.tmdeh.redisproduct.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class SignUpResponse {
    private Long id;
    private String email;

}
