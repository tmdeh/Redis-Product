package com.tmdeh.redisproduct.security.service;

import com.tmdeh.redisproduct.model.entity.Member;
import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetails extends UserDetails {
    Member getUser();
}
