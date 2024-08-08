package com.tmdeh.redisproduct.security.model;

import com.tmdeh.redisproduct.model.entity.Member;
import com.tmdeh.redisproduct.security.service.CustomUserDetails;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class CustomUserDetailsImpl implements CustomUserDetails {

    private final Member user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
