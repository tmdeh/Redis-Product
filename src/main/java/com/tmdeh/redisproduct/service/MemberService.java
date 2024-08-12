package com.tmdeh.redisproduct.service;

import com.tmdeh.redisproduct.exception.CustomException;
import com.tmdeh.redisproduct.exception.code.ErrorCode;
import com.tmdeh.redisproduct.model.dto.reqeust.LoginRequest;
import com.tmdeh.redisproduct.model.dto.reqeust.SignUpRequest;
import com.tmdeh.redisproduct.model.dto.response.ApiResponse;
import com.tmdeh.redisproduct.model.dto.response.LoginResponse;
import com.tmdeh.redisproduct.model.dto.response.SignUpResponse;
import com.tmdeh.redisproduct.model.entity.Member;
import com.tmdeh.redisproduct.repository.MemberRepository;
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
public class MemberService {

    private final MemberRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public ApiResponse<SignUpResponse> signUp(SignUpRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_EMAIL);
        }

        Member user = new Member(request.getEmail(), passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        return ApiResponse.of(HttpStatus.CREATED, HttpStatus.CREATED.getReasonPhrase(), Member.from(user));
    }

    @Transactional
    public ApiResponse<LoginResponse> login(LoginRequest request) {
        CustomUserDetails userDetails = authenticate(request.getEmail(), request.getPassword());

        String refreshToken = jwtTokenProvider.generateRefreshToken();
        String accessToken = jwtTokenProvider.generateAccessToken(userDetails.getMember().getId());

        return ApiResponse.success(new LoginResponse(accessToken, refreshToken));
    }

    private CustomUserDetails authenticate(String email, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            if (!(authentication.getPrincipal() instanceof CustomUserDetails userDetails)) {
                throw new CustomException(ErrorCode.LOGIN_FAILED);
            }

            return userDetails;
        } catch (AuthenticationException e) {
            throw new CustomException(ErrorCode.LOGIN_FAILED);
        }
    }

}
