package com.sparta.springpractice2.entity;

import com.sparta.springpractice2.dto.MemberRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private MemberEnum role;

    public Member(String username,  String password, MemberEnum role){
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
