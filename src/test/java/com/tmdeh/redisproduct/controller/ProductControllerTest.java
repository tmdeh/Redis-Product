package com.tmdeh.redisproduct.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmdeh.redisproduct.config.TestSecurityConfig;
import com.tmdeh.redisproduct.model.dto.reqeust.CreateProductRequest;
import com.tmdeh.redisproduct.util.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() throws Exception {
        token = jwtTokenProvider.generateAccessToken(1L);
    }

    @Test
    void 상품_삽입하기_성공() throws Exception{
        CreateProductRequest request = new CreateProductRequest(5000, 100, "테스트 상품입니다.", "테스트 상품");

        mockMvc.perform(post("/api/products")
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.data.id").exists());
    }
}