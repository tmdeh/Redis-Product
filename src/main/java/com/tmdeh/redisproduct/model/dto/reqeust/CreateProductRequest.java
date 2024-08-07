package com.tmdeh.redisproduct.model.dto.reqeust;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateProductRequest {

    private int price;
    private long stock;
    private String description;
    private String name;

}
