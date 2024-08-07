package com.tmdeh.redisproduct.service;

import com.tmdeh.redisproduct.model.dto.reqeust.CreateProductRequest;
import com.tmdeh.redisproduct.model.dto.response.ApiResponse;
import com.tmdeh.redisproduct.model.dto.response.CreateProductResponse;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    public ApiResponse<CreateProductResponse> createProduct(CreateProductRequest request) {
        return null;
    }

}
