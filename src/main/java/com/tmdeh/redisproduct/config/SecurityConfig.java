package com.tmdeh.redisproduct.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmdeh.redisproduct.security.fillter.JwtAuthenticationFilter;
import com.tmdeh.redisproduct.security.service.CustomMemberDetailService;
import com.tmdeh.redisproduct.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(request ->
                request.requestMatchers(HttpMethod.POST,"/api/users/signup", "/api/users/login").permitAll()
                        .anyRequest().authenticated()
        );

        http.addFilterAt(new JwtAuthenticationFilter(jwtTokenProvider, objectMapper), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, CustomMemberDetailService customUserDetailService, PasswordEncoder passwordEncoder) throws Exception {
        AuthenticationManagerBuilder authManagerBuilder =  http.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder.userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder);
        return authManagerBuilder.build();
    }
}
