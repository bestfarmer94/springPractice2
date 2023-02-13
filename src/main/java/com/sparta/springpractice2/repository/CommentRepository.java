package com.sparta.springpractice2.repository;

import com.sparta.springpractice2.entity.Board;
import com.sparta.springpractice2.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findByBoardAndContentAndUsername(Board board, String content, String subject);
}
