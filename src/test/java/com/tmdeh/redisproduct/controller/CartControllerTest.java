package com.tmdeh.redisproduct.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmdeh.redisproduct.exception.code.ErrorCode;
import com.tmdeh.redisproduct.model.dto.reqeust.CartRequest;
import com.tmdeh.redisproduct.model.dto.response.CartResponse;
import com.tmdeh.redisproduct.model.entity.Product;
import com.tmdeh.redisproduct.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductRepository productRepository;

    private Long productId;
    @Qualifier("redisTemplate")
    @Autowired
    private RedisTemplate redisTemplate;

    @BeforeEach
    void setUp() throws Exception {
        Product product = Product.builder()
                .price(5000)
                .stock(100L)
                .name("테스트 상품")
                .description("테스트 상품 설명")
                .build();
        Product savedProduct = productRepository.save(product);
        productId = savedProduct.getId();
    }

    @Test
    void 장바구니에_추가() throws Exception {
        CartRequest request = new CartRequest(50, productId);

        String redisKey = "cart:" + productId;
        redisTemplate.delete(redisKey);

        MvcResult result = mockMvc.perform(post("/api/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.productId").value(productId))
                .andReturn();

        // Then - API 응답 내용 검증
        String content = result.getResponse().getContentAsString();
        assertNotNull(content);  // 응답이 null이 아닌지 확인

        // Redis에 데이터가 저장되었는지 확인
        String redisValue = (String) redisTemplate.opsForValue().get(redisKey);
        assertNotNull(redisValue);  // Redis에 값이 있는지 확인

        // Redis에 저장된 값이 기대한 값인지 검증
        // Redis에 저장된 값이 JSON이라면 이를 객체로 역직렬화하여 검증합니다.
        CartResponse cartResponse = objectMapper.readValue(redisValue, CartResponse.class);

        assertThat(productId).isEqualTo(cartResponse.getProductId());
        assertThat(50).isEqualTo(cartResponse.getQuantity());
    }

    @Test
    void 장바구니에_추가_재고_이상으로_추가할_경우() throws Exception {
        CartRequest request = new CartRequest(Integer.MAX_VALUE, productId);

        // When - 장바구니에 추가 요청
        mockMvc.perform(post("/api/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                // Then - 재고 초과로 인한 오류 응답 검증
                .andExpect(status().isBadRequest())  // HTTP 상태 코드가 400 Bad Request인지 확인
                .andExpect(jsonPath("$.message").value(ErrorCode.OUT_OF_STOCK.getMessage()));
    }

    @Test
    void 장바구니_조회() throws Exception {
        mockMvc.perform(get("/api/cart")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].id").value(productId));
    }

    @Test
    void 장바구니_삭제() throws Exception {
        String redisKey = "cart:" + productId;

        mockMvc.perform(delete("/api/cart/{id}", productId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String redisValue = (String) redisTemplate.opsForValue().get(redisKey);
        assertThat(redisValue).isNull();
    }

    @AfterEach
    void afterEach() throws Exception {
        redisTemplate.delete("cart:" + productId);
    }

}