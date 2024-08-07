package com.tmdeh.redisproduct.service;

import com.tmdeh.redisproduct.model.dto.reqeust.CreateProductRequest;
import com.tmdeh.redisproduct.model.dto.response.ApiResponse;
import com.tmdeh.redisproduct.model.dto.response.CreateProductResponse;
import com.tmdeh.redisproduct.model.entity.Product;
import com.tmdeh.redisproduct.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ApiResponse<CreateProductResponse> createProduct(CreateProductRequest request) {
        Product product = productRepository.save(Product.of(request));
        return ApiResponse.of(HttpStatus.CREATED, HttpStatus.CREATED.name(), Product.from(product));
    }

}
