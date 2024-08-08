package com.tmdeh.redisproduct.repository;

import com.tmdeh.redisproduct.exception.CustomException;
import com.tmdeh.redisproduct.exception.code.ErrorCode;
import com.tmdeh.redisproduct.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    default Product getById(Long id) {
        return findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));
    }

}