package com.tmdeh.redisproduct.util;

import static org.assertj.core.api.Assertions.*;

import com.tmdeh.redisproduct.exception.CustomException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void 토큰_발급_access() {
        Long id = 0L;
        String accessToken = jwtTokenProvider.generateAccessToken(id);
        assertThat(accessToken).isNotNull();
        assertThat(accessToken).isNotBlank();
    }

    @Test
    void 토큰_발급_refresh() {
        String refreshToken = jwtTokenProvider.generateRefreshToken();
        assertThat(refreshToken).isNotNull();
        assertThat(refreshToken).isNotBlank();
    }

    @Test
    void 토큰_검증_access() {
        Long id = 0L;
        String accessToken = jwtTokenProvider.generateAccessToken(id);
        assertThatNoException().isThrownBy(() -> {
            jwtTokenProvider.validateAccessToken(accessToken);
        });
    }

    @Test
    void 토큰_검증_refresh() {
        String refreshToken = jwtTokenProvider.generateRefreshToken();

        assertThatNoException().isThrownBy(() -> {
            jwtTokenProvider.validateRefreshToken(refreshToken);
        });
    }



    @Test
    void 토큰이_변조된_경우_access() {
        // given
        String validToken = jwtTokenProvider.generateAccessToken(0L);

        // 토큰을 변조
        String tamperedToken = validToken.substring(0, validToken.length() - 1) + "x";  // 임의의 문자 추가

        // when & then
        assertThatThrownBy(() -> jwtTokenProvider.validateAccessToken(tamperedToken))
                .isInstanceOf(CustomException.class)  // JwtException의 서브클래스 예
                .hasMessageContaining("잘못된 토큰입니다.");  // 메시지는 실제 예외 메시지에 따라 조정 필요
    }

    @Test
    void 토큰이_변조된_경우_refresh() {
        // given
        String validRefreshToken = jwtTokenProvider.generateRefreshToken();

        // 토큰을 변조
        String tamperedRefreshToken = validRefreshToken.substring(0, validRefreshToken.length() - 1) + "x";  // 임의의 문자 추가

        // when & then
        assertThatThrownBy(() -> jwtTokenProvider.validateRefreshToken(tamperedRefreshToken))
                .isInstanceOf(CustomException.class)  // JwtException의 서브클래스 예
                .hasMessageContaining("잘못된 토큰입니다.");  // 메시지는 실제 예외 메시지에 따라 조정 필요
    }

    @Test
    void 토큰_정보_가져오기() {
        Long userId = 1L;
        String accessToken = jwtTokenProvider.generateAccessToken(userId);
         Long tokenUserId = jwtTokenProvider.getUserId(accessToken);

         assertThat(tokenUserId).isEqualTo(userId);
    }

}