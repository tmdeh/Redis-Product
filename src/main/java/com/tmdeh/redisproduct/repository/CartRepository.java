package com.tmdeh.redisproduct.repository;

import com.tmdeh.redisproduct.model.entity.Cart;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, Long> {

}