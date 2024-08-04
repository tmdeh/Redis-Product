package com.tmdeh.redisproduct.util;

import com.tmdeh.redisproduct.exception.CustomException;
import com.tmdeh.redisproduct.exception.code.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${secret.access.expire}")
    private Long accessExpire;

    @Value("${secret.refresh.expire}")
    private Long refreshExpire;

    @Value("${secret.access.key}")
    private String accessKey;

    @Value("${secret.refresh.key}")
    private String refreshKey;


    public String generateAccessToken(Long id) {
        return Jwts.builder()
                .claim("userId", id)
                .setIssuer("Redis Product")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpire))
                .signWith(getAccessSecretKey())
                .compact();
    }

    public String generateRefreshToken() {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setIssuer("Redis Product")
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpire))
                .signWith(getRefreshSecretKey())
                .compact();
    }


    public void validateAccessToken(String accessToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getAccessSecretKey())
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
                    .setSigningKey(getRefreshSecretKey())
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

    public Long getUserId(String accessToken) {
        return Jwts.parserBuilder()
                .setSigningKey(getAccessSecretKey())
                .build().parseClaimsJws(accessToken)
                .getBody()
                .get("userId", Long.class);
    }


    private SecretKey getAccessSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(accessKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private SecretKey getRefreshSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(refreshKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
