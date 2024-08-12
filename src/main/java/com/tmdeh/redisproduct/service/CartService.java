package com.tmdeh.redisproduct.service;

import com.tmdeh.redisproduct.model.dto.reqeust.CartRequest;
import com.tmdeh.redisproduct.model.dto.response.CartResponse;
import com.tmdeh.redisproduct.model.entity.Member;
import com.tmdeh.redisproduct.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public CartResponse insertCart(CartRequest cartRequest, Member member) {

    }
}
