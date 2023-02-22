package com.sparta.springpractice2.service;

import com.sparta.springpractice2.dto.CommentRequestDto;
import com.sparta.springpractice2.dto.CommentResponseDto;
import com.sparta.springpractice2.entity.*;
import com.sparta.springpractice2.repository.BoardRepository;
import com.sparta.springpractice2.repository.CommentLikeRepository;
import com.sparta.springpractice2.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public CommentResponseDto writeComment(Long boardId, CommentRequestDto commentRequestDto, Member member) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );

        Comment comment = commentRepository.save(new Comment(board, commentRequestDto, member));
        board.addComment(comment);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public String updateLikeComment(Long commentId, Member member) {
        Comment comment = getComment(commentId);
        Optional<CommentLike> commentLike = commentLikeRepository.findCommentLikeByMemberAndComment(member, comment);

        if (commentLike.isPresent()) {
            comment.removeLike(commentLike.get());
            return "좋아요 취소";
        }
        comment.addLike(new CommentLike(member, comment));
        return "좋아요";
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto, Member member) {
        Comment comment = getComment(commentId);
        confirm(member, comment);

        comment.update(commentRequestDto);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public String deleteComment(Long commentId, Member member) {
        Comment comment = getComment(commentId);
        confirm(member, comment);

        commentLikeRepository.deleteByComment(comment);
        commentRepository.delete(comment);
        return "삭제 완료.";
    }

    private void confirm(Member member, Comment comment) {
        if (member.getRole() == MemberEnum.ADMIN || member.getUsername().equals(comment.getMember().getUsername())) {
            return;
        }
        throw new IllegalArgumentException("해당 댓글은 회원님의 댓글이 아닙니다.");
    }

    private Comment getComment(Long commentId) {
        Comment comment = commentRepository.findByCommentId(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );
        return comment;
    }

}
