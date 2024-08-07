package com.tmdeh.redisproduct.util;

import com.tmdeh.redisproduct.exception.CustomException;
import com.tmdeh.redisproduct.exception.code.ErrorCode;
import com.tmdeh.redisproduct.security.service.CustomUserDetailService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${secret.access.expire}")
    private Long accessExpire;

    @Value("${secret.refresh.expire}")
    private Long refreshExpire;

    @Value("${secret.access.key}")
    private String accessString;

    @Value("${secret.refresh.key}")
    private String refreshString;

    private SecretKey accessKey;
    private SecretKey refreshKey;


    @PostConstruct
    public void init() {
        accessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessString));
        refreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshString));
    }


    private final CustomUserDetailService userDetailsService;

    public String generateAccessToken(Long id) {
        return Jwts.builder()
                .claim("userId", id)
                .setIssuer("Redis Product")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpire))
                .signWith(accessKey)
                .compact();
    }

    public String generateRefreshToken() {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setIssuer("Redis Product")
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpire))
                .signWith(refreshKey)
                .compact();
    }


    public void validateAccessToken(String accessToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(accessKey)
                    .build()
                    .parseClaimsJws(accessToken);
        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우
            throw new CustomException(ErrorCode.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            // 지원되지 않는 JWT인 경우
            throw new CustomException(ErrorCode.UNSUPPORTED_TOKEN);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }


    public void validateRefreshToken(String refreshToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(refreshKey)
                    .build()
                    .parseClaimsJws(refreshToken);
        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우
            throw new CustomException(ErrorCode.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            // 지원되지 않는 JWT인 경우
            throw new CustomException(ErrorCode.UNSUPPORTED_TOKEN);
        } catch (Exception e) {
            // 잘못된 토큰인 경우
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }


    public String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            throw new CustomException(ErrorCode.TOKEN_NOTFOUND);
        }
        return bearerToken.substring(7); // "Bearer " 이후의 실제 토큰 부분을 추출합니다.
    }

    public Long getUserId(String accessToken) {
        return Jwts.parserBuilder()
                .setSigningKey(accessKey)
                .build().parseClaimsJws(accessToken)
                .getBody()
                .get("userId", Long.class);
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserById(getUserId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

}
