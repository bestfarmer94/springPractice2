package com.sparta.springpractice2.entity;

import com.sparta.springpractice2.dto.BoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Board extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private Member member;
    private String content;

    @OneToMany
    private List<Comment> commentList = new ArrayList<>();

    public Board(BoardRequestDto boardRequestDto, Member member){
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
        this.member = member;
    }

    public void addComment(Comment comment){
        commentList.add(comment);
    }

    public void update(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
    }
}
