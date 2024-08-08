package com.tmdeh.redisproduct.security.fillter;

import com.tmdeh.redisproduct.exception.code.ErrorCode;
import com.tmdeh.redisproduct.model.dto.reqeust.SignUpRequest;
import com.tmdeh.redisproduct.model.dto.response.ApiResponse;
import com.tmdeh.redisproduct.model.dto.response.SignUpResponse;
import com.tmdeh.redisproduct.service.MemberService;
import com.tmdeh.redisproduct.util.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class JwtAuthenticationFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private MemberService usersService;

    private final String endpoint = "/api/users/profile";

    private String token;

    private String expiredToken = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjM3LCJpc3MiOiJSZWRpcyBQcm9kdWN0IiwiaWF0IjoxNzIyOTMwNDQ2LCJleHAiOjE3MjI5MzEwNDZ9.nHIEvHXf4Dne_6ioQA2D0P_QfIogrlu4wusxtOlMKf0";

    @BeforeEach
    public void setUp() throws Exception {
        ApiResponse<SignUpResponse> response = usersService.signUp(new SignUpRequest("test@test.com", "test"));
        token = jwtTokenProvider.generateAccessToken(response.getData().getId());
    }

    @Test
    void 유효한토큰이_있는경우_성공적으로_인증한다() throws Exception {
        // when, then
        mockMvc.perform(get(endpoint)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void 유효하지않은토큰이_있는경우_401에러를_반환한다() throws Exception {
        mockMvc.perform(get(endpoint)
                        .header("Authorization", "Bearer " + token + "wrong value"))
                .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.message").value(ErrorCode.INVALID_TOKEN.getMessage()));
    }

    @Test
    void 토큰이_없는경우_401에러를_반환한다() throws Exception {
        mockMvc.perform(get(endpoint))
                .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.message").value(ErrorCode.TOKEN_NOTFOUND.getMessage()));
    }

    @Test
        void 만료된토큰이_있는경우_401에러를_반환한다() throws Exception {
            mockMvc.perform(get(endpoint)
                            .header("Authorization", "Bearer " + expiredToken))
                    .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value(ErrorCode.EXPIRED_TOKEN.getMessage()));
        }
}
