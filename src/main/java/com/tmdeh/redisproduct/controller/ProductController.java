package com.tmdeh.redisproduct.controller;

import com.tmdeh.redisproduct.model.dto.reqeust.CreateProductRequest;
import com.tmdeh.redisproduct.model.dto.response.ApiResponse;
import com.tmdeh.redisproduct.model.dto.response.ProductResponse;
import com.tmdeh.redisproduct.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@RequestBody CreateProductRequest request) {
        ApiResponse<ProductResponse> response = productService.createProduct(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProducts() {
        ApiResponse<List<ProductResponse>> response = productService.findAllProduct();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProduct(@PathVariable Long productId) {
        ApiResponse<ProductResponse> response = productService.findProductById(productId);
        return ResponseEntity.ok(response);
    }

}
