package com.sparta.springpractice2.dto;

import lombok.Getter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class MemberDto {
    @Size(min = 4, max = 10)
    @Pattern(regexp = "[a-z0-9]+")
    private String username;
    @Size(min = 8, max = 15)
    @Pattern(regexp = "[a-zA-Z0-9]+")
    private String password;
}
