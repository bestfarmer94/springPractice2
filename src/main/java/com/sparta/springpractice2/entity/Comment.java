package com.sparta.springpractice2.entity;

import com.sparta.springpractice2.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID", nullable = false)
    private Board board;

    public Comment(Board board, CommentRequestDto commentRequestDto, Member member) {
        this.content = commentRequestDto.getContent();
        this.member = member;
        this.board = board;
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
    }
}
