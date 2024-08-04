package com.tmdeh.redisproduct.repository;

import com.tmdeh.redisproduct.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

}