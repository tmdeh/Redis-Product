package com.tmdeh.redisproduct.repository;

import com.tmdeh.redisproduct.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}