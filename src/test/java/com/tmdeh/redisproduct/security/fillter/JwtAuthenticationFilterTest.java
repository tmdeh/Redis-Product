package com.tmdeh.redisproduct.security.fillter;

import com.tmdeh.redisproduct.model.dto.reqeust.SignUpRequest;
import com.tmdeh.redisproduct.model.dto.response.ApiResponse;
import com.tmdeh.redisproduct.model.dto.response.SignUpResponse;
import com.tmdeh.redisproduct.service.UsersService;
import com.tmdeh.redisproduct.util.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class JwtAuthenticationFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private final String endpoint = "/api/users/profile";
    @Autowired
    private UsersService usersService;

    private Long userId;

    @BeforeEach
    public void setUp() throws Exception {
        ApiResponse<SignUpResponse> response = usersService.signUp(new SignUpRequest("test@test.com", "test"));
        userId = response.getData().getId();
    }

    @Test
    void 유효한토큰이_있는경우_성공적으로_인증한다() throws Exception {


        String token = jwtTokenProvider.generateAccessToken(userId);
        // when, then
        mockMvc.perform(get(endpoint)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void 유효하지않은토큰이_있는경우_401에러를_반환한다() throws Exception {

        String token = "";


//        when(jwtTokenProvider.resolveToken(any(HttpServletRequest.class))).thenReturn(token);
//        when(jwtTokenProvider.validateToken(token)).thenReturn(false);

        // when, then
        mockMvc.perform(get(endpoint)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void 토큰이_없는경우_401에러를_반환한다() throws Exception {

        // when, then
        mockMvc.perform(get(endpoint))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void 만료된토큰이_있는경우_401에러를_반환한다() throws Exception {

        String token = "";

//        when(jwtTokenProvider.resolveToken(any(HttpServletRequest.class))).thenReturn(token);
//        when(jwtTokenProvider.validateToken(token)).thenReturn(false);

        // when, then
        mockMvc.perform(get(endpoint)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isUnauthorized());
    }
}
