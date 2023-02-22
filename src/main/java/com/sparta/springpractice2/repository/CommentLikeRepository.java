package com.sparta.springpractice2.repository;

import com.sparta.springpractice2.entity.Comment;
import com.sparta.springpractice2.entity.CommentLike;
import com.sparta.springpractice2.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findCommentLikeByMemberAndComment(Member member, Comment comment);
    List<CommentLike> deleteByComment(Comment comment);

}
