package com.tmdeh.redisproduct.security.service;

import com.tmdeh.redisproduct.model.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetails extends UserDetails {
    User getUser();
}
