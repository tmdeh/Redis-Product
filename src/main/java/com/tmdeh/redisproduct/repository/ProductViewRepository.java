package com.tmdeh.redisproduct.repository;

import com.tmdeh.redisproduct.model.entity.ProductView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductViewRepository extends JpaRepository<ProductView, Long> {

}