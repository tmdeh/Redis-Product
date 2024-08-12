package com.tmdeh.redisproduct.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Entity
@Builder
@RedisHash("cart")
@NoArgsConstructor
@AllArgsConstructor
public class Cart extends TimeStamp{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private Integer quantity; // 개수


//    public static Cart of(CartRequest request, Member member, Product product) {
//        return Cart.builder()
//            .member(member)
//            .product(product)
//            .quantity(request.getQuantity())
//            .build();
//    }

}
