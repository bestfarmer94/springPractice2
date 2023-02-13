package com.sparta.springpractice2.entity;

import com.sparta.springpractice2.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String username;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID", nullable = false)
    private Board board;

    public Comment(Board board, CommentRequestDto commentRequestDto, String username) {
        this.content = commentRequestDto.getContent();
        this.username = username;
        this.board = board;
    }
}
