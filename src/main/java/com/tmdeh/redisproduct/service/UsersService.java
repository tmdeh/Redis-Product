package com.tmdeh.redisproduct.service;

import com.tmdeh.redisproduct.exception.CustomException;
import com.tmdeh.redisproduct.exception.code.ErrorCode;
import com.tmdeh.redisproduct.model.dto.reqeust.LoginRequest;
import com.tmdeh.redisproduct.model.dto.reqeust.SignUpRequest;
import com.tmdeh.redisproduct.model.dto.response.ApiResponse;
import com.tmdeh.redisproduct.model.dto.response.LoginResponse;
import com.tmdeh.redisproduct.model.dto.response.SignUpResponse;
import com.tmdeh.redisproduct.model.entity.User;
import com.tmdeh.redisproduct.repository.UserRepository;
import com.tmdeh.redisproduct.security.service.CustomUserDetails;
import com.tmdeh.redisproduct.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public ApiResponse<SignUpResponse> signUp(SignUpRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_EMAIL);
        }

        User user = new User(request.getEmail(), passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        return ApiResponse.of(HttpStatus.CREATED, HttpStatus.CREATED.getReasonPhrase(), SignUpResponse.from(user));
    }

    @Transactional
    public ApiResponse<LoginResponse> login(LoginRequest request) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (AuthenticationException e) {
            // 인증 실패 시 예외 처리
            throw new CustomException(ErrorCode.LOGIN_FAILED);
        }

        // Principal 객체가 CustomUserDetails인지 확인
        if (!(authentication.getPrincipal() instanceof CustomUserDetails)) {
            throw new CustomException(ErrorCode.LOGIN_FAILED);
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String refreshToken = jwtTokenProvider.generateRefreshToken();
        String accessToken = jwtTokenProvider.generateAccessToken(userDetails.getUser().getId());

        return ApiResponse.success(new LoginResponse(accessToken, refreshToken));
    }

}
