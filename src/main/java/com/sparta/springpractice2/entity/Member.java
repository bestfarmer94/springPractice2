package com.sparta.springpractice2.entity;

import com.sparta.springpractice2.dto.MemberDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;

    public Member(MemberDto memberDto){
        this.username = memberDto.getUsername();
        this.password = memberDto.getPassword();
    }
}
