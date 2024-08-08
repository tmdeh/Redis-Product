package com.tmdeh.redisproduct.security.service;

import com.tmdeh.redisproduct.exception.CustomException;
import com.tmdeh.redisproduct.exception.code.ErrorCode;
import com.tmdeh.redisproduct.model.entity.Member;
import com.tmdeh.redisproduct.repository.MemberRepository;
import com.tmdeh.redisproduct.security.model.CustomUserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomMemberDetailService implements UserDetailsService {

    private final MemberRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member user = userRepository.findByEmail(email)
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOTFOUND));
        return new CustomUserDetailsImpl(user);
    }

    public UserDetails loadUserById(Long id) {
        Member user = userRepository.getById(id);
        return new CustomUserDetailsImpl(user);
    }

}
