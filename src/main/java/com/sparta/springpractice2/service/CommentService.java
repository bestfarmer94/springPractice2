package com.sparta.springpractice2.service;

import com.sparta.springpractice2.dto.CommentRequestDto;
import com.sparta.springpractice2.dto.CommentResponseDto;
import com.sparta.springpractice2.entity.Board;
import com.sparta.springpractice2.entity.Comment;
import com.sparta.springpractice2.entity.Member;
import com.sparta.springpractice2.entity.MemberEnum;
import com.sparta.springpractice2.jwt.JwtUtil;
import com.sparta.springpractice2.repository.CommentRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;
    private final MemberService memberService;
    private final BoardService boardService;

    @Transactional
    public CommentResponseDto writeComment(Long boardId, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        Claims claims = jwtUtil.validToken(request);
        Board board = boardService.getBoard(boardId);
        Member member = memberService.getMember(claims);

        Comment comment = commentRepository.save(new Comment(board, commentRequestDto, member));
        board.addComment(comment);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        Claims claims = jwtUtil.validToken(request);
        Member member = memberService.getMember(claims);
        Comment comment = getComment(commentId);
        confirm(member, comment);

        comment.update(commentRequestDto);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public String deleteComment(Long commentId, HttpServletRequest request) {
        Claims claims = jwtUtil.validToken(request);
        Member member = memberService.getMember(claims);
        Comment comment = getComment(commentId);
        confirm(member, comment);

        commentRepository.delete(comment);
        return "삭제 완료.";
    }

    private void confirm(Member member, Comment comment) {
        if(member.getRole() == MemberEnum.ADMIN || member == comment.getMember()){
            return;
        }
        throw new IllegalArgumentException("삭제 권한이 없습니다.");
    }

    private Comment getComment(Long commentId) {
        Comment comment = commentRepository.findByCommentId(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );
        return comment;
    }
}
