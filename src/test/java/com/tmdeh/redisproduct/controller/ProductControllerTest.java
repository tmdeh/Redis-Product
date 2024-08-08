package com.tmdeh.redisproduct.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmdeh.redisproduct.config.TestSecurityConfig;
import com.tmdeh.redisproduct.model.dto.reqeust.CreateProductRequest;
import com.tmdeh.redisproduct.model.entity.Product;
import com.tmdeh.redisproduct.repository.ProductRepository;
import com.tmdeh.redisproduct.util.JwtTokenProvider;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private ProductRepository productRepository;

    private List<Product> products;

    @BeforeEach
    void setUp() {
        token = jwtTokenProvider.generateAccessToken(1L);

        products = new ArrayList<>();
        for(int i = 1; i <= 5; i++) {
            products.add(new Product("테스트 상품" + i, "테스트 상품입니다.", 5000 * i, 100L));
        }
        productRepository.saveAll(products);
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


    @Test
    void 상품_전체_조회() throws Exception {
        mockMvc.perform(get("/api/products")
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data").isArray())
            .andExpect(jsonPath("$.data.length()").value(products.size()))
            .andExpect(jsonPath("$.data[0].id").value(products.get(0).getId()))
            .andExpect(jsonPath("$.data[0].name").value(products.get(0).getName()))
            .andExpect(jsonPath("$.data[0].price").value(products.get(0).getPrice()))
            .andExpect(jsonPath("$.data[0].description").value(products.get(0).getDescription()));
    }


    @Test
    void 상품_상세_조회_성공() throws Exception {
        long productId = 1L;

        mockMvc.perform(get("/api/products/{productId}", productId)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.id").value(productId))
            .andExpect(jsonPath("$.data.name").value(products.get(0).getName()))
            .andExpect(jsonPath("$.data.price").value(products.get(0).getPrice()))
            .andExpect(jsonPath("$.data.description").value(products.get(0).getDescription()));

    }

    @Test
    void 상품_상세_조회_실패_해당_제품이_없는_경우() throws Exception {

        long productId = Long.MAX_VALUE;

        mockMvc.perform(get("/api/products/{productId}", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isNotFound());
    }

}