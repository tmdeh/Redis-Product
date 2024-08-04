package com.tmdeh.redisproduct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class RedisProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisProductApplication.class, args);
    }

}
