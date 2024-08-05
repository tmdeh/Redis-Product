package com.tmdeh.redisproduct.model.dto.response;

import com.tmdeh.redisproduct.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class SignUpResponse {
    private Long id;
    private String email;

    public static SignUpResponse from(User user) {
        return new SignUpResponse(user.getId(), user.getEmail());
    }

}
