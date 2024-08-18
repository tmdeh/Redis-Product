package com.tmdeh.redisproduct.controller;

import com.tmdeh.redisproduct.model.dto.reqeust.LoginRequest;
import com.tmdeh.redisproduct.model.dto.reqeust.SignUpRequest;
import com.tmdeh.redisproduct.model.dto.response.ApiResponse;
import com.tmdeh.redisproduct.model.dto.response.LoginResponse;
import com.tmdeh.redisproduct.model.dto.response.SignUpResponse;
import com.tmdeh.redisproduct.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService usersService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignUpResponse>> signup(@RequestBody SignUpRequest request) {
        ApiResponse<SignUpResponse> apiResponse = usersService.signUp(request);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        ApiResponse<LoginResponse> apiResponse = usersService.login(request);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        return ResponseEntity.ok("hello");
    }

}
