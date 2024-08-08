package com.tmdeh.redisproduct.repository;

import com.tmdeh.redisproduct.exception.CustomException;
import com.tmdeh.redisproduct.exception.code.ErrorCode;
import com.tmdeh.redisproduct.model.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    boolean existsByEmail(@NonNull String email);

    default Member getById(@NonNull Long id) {
        return findById(id).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOTFOUND));
    }

}