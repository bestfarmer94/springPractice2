package com.sparta.springpractice2.dto;

import com.sparta.springpractice2.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private String username;
    private String content;
    private LocalDateTime createdAt;

    public CommentResponseDto(Comment comment){
        this.username = comment.getUsername();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
    }
}
