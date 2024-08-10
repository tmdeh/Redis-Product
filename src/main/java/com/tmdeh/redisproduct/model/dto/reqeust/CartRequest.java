package com.tmdeh.redisproduct.model.dto.reqeust;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartRequest {

    private Integer quantity;

    private Long productId;

}
