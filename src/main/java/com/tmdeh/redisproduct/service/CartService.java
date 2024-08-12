package com.tmdeh.redisproduct.service;

import com.tmdeh.redisproduct.model.dto.reqeust.CartRequest;
import com.tmdeh.redisproduct.model.dto.response.ApiResponse;
import com.tmdeh.redisproduct.model.dto.response.CartResponse;
import com.tmdeh.redisproduct.model.entity.Member;
import com.tmdeh.redisproduct.model.entity.Product;
import com.tmdeh.redisproduct.repository.CartRepository;
import com.tmdeh.redisproduct.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public ApiResponse<CartResponse> insertCart(CartRequest cartRequest, Member member) {
        Product product = productRepository.getById(cartRequest.getProductId());
        return null;
    }
}
