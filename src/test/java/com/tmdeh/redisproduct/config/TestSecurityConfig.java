package com.tmdeh.redisproduct.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@TestConfiguration
public class TestSecurityConfig {

    @Bean
    public SecurityFilterChain filterChainTest(HttpSecurity http) throws Exception {

        http.formLogin(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(request ->
            request.requestMatchers(HttpMethod.POST,"/api/users/signup", "/api/users/login").permitAll()
                .anyRequest().permitAll()
        );


        return http.build();
    }

}
