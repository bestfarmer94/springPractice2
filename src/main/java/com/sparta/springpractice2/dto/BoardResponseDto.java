package com.sparta.springpractice2.dto;

import com.sparta.springpractice2.entity.Board;
import com.sparta.springpractice2.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class BoardResponseDto {
    private Long boardId;
    private String title;
    private String username;
    private String content;
    private LocalDateTime createdAt;
    private List<CommentResponseDto> commentList;
    private int likes;


    public BoardResponseDto(Board board) {
        this.boardId = board.getBoardId();
        this.title = board.getTitle();
        this.username = board.getMember().getUsername();
        this.content = board.getContent();
        this.createdAt = board.getCreatedAt();

        List<CommentResponseDto> list = new ArrayList<>();
        for (Comment comment : board.getCommentList()) {
            list.add(new CommentResponseDto(comment));
        }
        Collections.sort(list);
        this.commentList = list;
        this.likes = board.getLikes();
    }
}
