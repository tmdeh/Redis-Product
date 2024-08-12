package com.tmdeh.redisproduct.controller;

import com.tmdeh.redisproduct.model.dto.reqeust.CartRequest;
import com.tmdeh.redisproduct.model.dto.response.ApiResponse;
import com.tmdeh.redisproduct.model.dto.response.CartResponse;
import com.tmdeh.redisproduct.model.entity.Member;
import com.tmdeh.redisproduct.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<ApiResponse<CartResponse>> insertCart(
        @RequestBody
        CartRequest request,
        @AuthenticationPrincipal
        Member member
    ) {
        ApiResponse<CartResponse> response = cartService.insertCart(request, member);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
