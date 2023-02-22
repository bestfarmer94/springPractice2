package com.sparta.springpractice2.entity;

import com.sparta.springpractice2.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    List<CommentLike> commentLikeList = new ArrayList<>();
//    private int likes;

    public Comment(Board board, CommentRequestDto commentRequestDto, Member member) {
        this.content = commentRequestDto.getContent();
        this.member = member;
        this.board = board;
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
    }

    public void removeLike(CommentLike commentLike) {
        commentLikeList.remove(commentLike);
    }

    public void addLike(CommentLike commentLike) {
        commentLikeList.add(commentLike);
    }
//    public void updateLike(boolean exists){
//        int addLike = exists? -1 : 1;
//        likes += addLike;
//    }
}
