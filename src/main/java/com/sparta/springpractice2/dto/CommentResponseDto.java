package com.sparta.springpractice2.dto;

import com.sparta.springpractice2.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto implements Comparable<CommentResponseDto>{
    private Long commentId;
    private String username;
    private String content;
    private LocalDateTime createdAt;

    public CommentResponseDto(Comment comment){
        this.commentId = comment.getCommentId();
        this.username = comment.getMember().getUsername();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
    }

    @Override
    public int compareTo(CommentResponseDto o) {
        return o.createdAt.compareTo(this.getCreatedAt());
    }
}
