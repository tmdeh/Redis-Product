package com.tmdeh.redisproduct.model.entity;

import com.tmdeh.redisproduct.model.dto.response.SignUpResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Member extends TimeStamp{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Cart> carts;

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }


    public static SignUpResponse from(Member user) {
        return new SignUpResponse(user.getId(), user.getEmail());
    }

}
