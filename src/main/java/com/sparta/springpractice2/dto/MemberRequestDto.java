package com.sparta.springpractice2.dto;

import com.sparta.springpractice2.entity.MemberEnum;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class MemberRequestDto {
    @Size(min = 4, max = 10)
    @Pattern(regexp = "[a-z0-9]+")
    private String username;
    @Size(min = 8, max = 15)
    private String password;
    @Enumerated(EnumType.STRING)
    private MemberEnum role;
}
