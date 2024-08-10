package com.tmdeh.redisproduct.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
    private Long id;

    private Long productId;

    private Integer quantity; // 개수
}
