package com.tmdeh.redisproduct.repository;

import com.tmdeh.redisproduct.model.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}