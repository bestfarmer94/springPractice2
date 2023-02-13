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
    private String username;
    private String content;

    @OneToMany
    private List<Comment> commentList = new ArrayList<>();

    public Board(BoardRequestDto boardRequestDto, String username){
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
        this.username = username;
    }

    public void addComment(Comment comment){
        System.out.println("addComment");

        commentList.add(comment);
        if(commentList.size() != 0){
            System.out.println(commentList.size());
            System.out.println(commentList.get(commentList.size()-1).getContent());
        }
    }
}
