package com.sparta.springpractice2.repository;

import com.sparta.springpractice2.entity.Board;
import com.sparta.springpractice2.entity.BoardLike;
import com.sparta.springpractice2.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    List<BoardLike> deleteByBoard(Board board);
    Optional<BoardLike> findBoardLikeByMemberAndBoard(Member member, Board board);
}
