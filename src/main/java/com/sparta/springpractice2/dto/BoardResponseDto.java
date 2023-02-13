package com.sparta.springpractice2.dto;

import com.sparta.springpractice2.entity.Board;
import com.sparta.springpractice2.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class BoardResponseDto {
    private String title;
    private String username;
    private String content;
    private LocalDateTime createdAt;
    private List<CommentResponseDto> commentList;

    public BoardResponseDto(Board board) {
        this.title = board.getTitle();
        this.username = board.getUsername();
        this.content = board.getContent();
        this.createdAt = board.getCreatedAt();

        List<CommentResponseDto> list = new ArrayList<>();
        for (Comment comment : board.getCommentList()) {
            list.add(new CommentResponseDto(comment));
        }

        this.commentList = list;
    }
}
