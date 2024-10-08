package com.tmdeh.redisproduct.service;

import com.tmdeh.redisproduct.model.dto.reqeust.CreateProductRequest;
import com.tmdeh.redisproduct.model.dto.response.ApiResponse;
import com.tmdeh.redisproduct.model.dto.response.ProductResponse;
import com.tmdeh.redisproduct.model.entity.Product;
import com.tmdeh.redisproduct.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private static final String PRODUCT_CACHE_KEY_PREFIX = "product:";
    private final RedisTemplate redisTemplate;

    @Transactional
    public ApiResponse<ProductResponse> createProduct(CreateProductRequest request) {
        Product product = productRepository.save(Product.of(request));;
        return ApiResponse.of(HttpStatus.CREATED, HttpStatus.CREATED.name(), Product.from(product));
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<ProductResponse>> findAllProduct() {
        List<ProductResponse> productList = productRepository.findAll().stream().map(Product::from)
            .toList();

        return ApiResponse.success(productList);
    }

    @Transactional(readOnly = true)
    public ApiResponse<ProductResponse> findProductById(Long id) {
        String cacheKey = PRODUCT_CACHE_KEY_PREFIX + id;
        Product product = (Product) redisTemplate.opsForValue().get(cacheKey);
        if (product == null) {
            product = productRepository.getById(id);
            redisTemplate.opsForValue().set(cacheKey, product);
        }
        return ApiResponse.success(Product.from(product));
    }

}

