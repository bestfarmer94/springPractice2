package com.sparta.springpractice2.service;

import com.sparta.springpractice2.dto.CommentRequestDto;
import com.sparta.springpractice2.dto.CommentResponseDto;
import com.sparta.springpractice2.entity.Board;
import com.sparta.springpractice2.entity.Comment;
import com.sparta.springpractice2.jwt.JwtUtil;
import com.sparta.springpractice2.repository.BoardRepository;
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
    private final BoardRepository boardRepository;

    @Transactional
    public String writeComment(Long boardId, CommentRequestDto commentRequestDto, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if(token != null){
            if(jwtUtil.validateToken(token)){
                claims = jwtUtil.getUserInfoFromToken(token);
            }else{
                throw new IllegalArgumentException("Token Error");
            }

            Board board = boardRepository.findById(boardId).orElseThrow(
                    () -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다.")
            );
            commentRepository.saveAndFlush(new Comment(board, commentRequestDto, claims.getSubject()));
            Comment comment = commentRepository.findByBoardAndContentAndUsername(board, commentRequestDto.getContent(), claims.getSubject());
            System.out.println(comment.getUsername());
            System.out.println("title :" + comment.getBoard().getTitle());
            board.addComment(comment);
            return "저장 성공";
        }
        return "저장 실패";
    }
}
