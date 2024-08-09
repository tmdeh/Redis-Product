package com.tmdeh.redisproduct.model.entity;

import com.tmdeh.redisproduct.model.dto.reqeust.CreateProductRequest;
import com.tmdeh.redisproduct.model.dto.response.ProductResponse;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Product extends TimeStamp implements Serializable  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String description;

    private Integer price;

    private Long stock; // 제고

    @Builder
    public Product(String name, String description, Integer price, Long stock) {
        validatePrice(price);
        validateStock(stock);
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }


    public static Product of(CreateProductRequest request) {
        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .build();
    }


    public static ProductResponse from(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }


    private void validatePrice(Integer price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }

    private void validateStock(Long stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
    }

}
