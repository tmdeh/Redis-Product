package com.tmdeh.redisproduct.controller;

import com.tmdeh.redisproduct.model.dto.reqeust.CartRequest;
import com.tmdeh.redisproduct.model.dto.response.CartResponse;
import com.tmdeh.redisproduct.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartResponse> insertCart(@RequestBody CartRequest request) {
        return null;
    }

}
