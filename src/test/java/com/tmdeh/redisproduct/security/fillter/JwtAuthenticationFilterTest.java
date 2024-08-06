package com.tmdeh.redisproduct.security.fillter;

import com.tmdeh.redisproduct.util.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class JwtAuthenticationFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void 유효한토큰이_있는경우_성공적으로_인증한다() throws Exception {
        // given
        String token = "valid-jwt-token";
        Authentication authentication = new UsernamePasswordAuthenticationToken("user", null, Collections.emptyList());

//        when(jwtTokenProvider.resolveToken(any(HttpServletRequest.class))).thenReturn(token);
//        when(jwtTokenProvider.validateToken(token)).thenReturn(true);
//        when(jwtTokenProvider.getAuthentication(token)).thenReturn(authentication);


        // when, then
        mockMvc.perform(get("/some-secured-endpoint")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void 유효하지않은토큰이_있는경우_401에러를_반환한다() throws Exception {
        // given
        String token = "invalid-jwt-token";

//        when(jwtTokenProvider.resolveToken(any(HttpServletRequest.class))).thenReturn(token);
//        when(jwtTokenProvider.validateToken(token)).thenReturn(false);

        // when, then
        mockMvc.perform(get("/some-secured-endpoint")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void 토큰이_없는경우_401에러를_반환한다() throws Exception {
        // given
//        when(jwtTokenProvider.resolveToken(any(HttpServletRequest.class))).thenReturn(null);

        // when, then
        mockMvc.perform(get("/some-secured-endpoint"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void 만료된토큰이_있는경우_401에러를_반환한다() throws Exception {
        // given
        String token = "expired-jwt-token";

//        when(jwtTokenProvider.resolveToken(any(HttpServletRequest.class))).thenReturn(token);
//        when(jwtTokenProvider.validateToken(token)).thenReturn(false);

        // when, then
        mockMvc.perform(get("/some-secured-endpoint")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isUnauthorized());
    }
}
