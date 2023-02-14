package com.sparta.springpractice2.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class BoardRequestDto {
    @NotNull
    private String title;
    @NotNull
    private String content;
}
