package com.tmdeh.redisproduct.security.fillter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmdeh.redisproduct.exception.CustomException;
import com.tmdeh.redisproduct.model.dto.response.ApiResponse;
import com.tmdeh.redisproduct.util.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final List<String> EXCLUDED_PATHS = List.of(
        "/api/users/login", // 로그인 엔드포인트
        "/api/users/signup" // 회원가입 엔드포인트
    );


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // 로그인 요청이나 특정 경로는 필터를 적용하지 않도록 설정
        String path = request.getRequestURI();
        return EXCLUDED_PATHS.stream().anyMatch(path::startsWith);
    }

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws IOException {
        try {
            String token = jwtTokenProvider.extractJwtFromRequest(request);

            jwtTokenProvider.validateAccessToken(token);

            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (CustomException e) {
            handleException(response, e);
        }

    }

    private void handleException(HttpServletResponse response, CustomException ex)
        throws IOException {
        response.setStatus(ex.getStatusCode().value());
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.error(ex)));
    }


}
