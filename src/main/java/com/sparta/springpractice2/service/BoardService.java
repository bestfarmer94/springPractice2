package com.sparta.springpractice2.service;

import com.sparta.springpractice2.dto.BoardRequestDto;
import com.sparta.springpractice2.dto.BoardResponseDto;
import com.sparta.springpractice2.entity.Board;
import com.sparta.springpractice2.jwt.JwtUtil;
import com.sparta.springpractice2.repository.BoardRepository;
import com.sparta.springpractice2.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<BoardResponseDto> listAll() {
        List<Board> list = boardRepository.findAllByOrderByCreatedAtDesc();
        List<BoardResponseDto> result = new ArrayList<>();
        for (Board board : list) {
            result.add(new BoardResponseDto(board));
        }

        return result;
    }

    @Transactional
    public String writeBoard(BoardRequestDto boardRequestDto, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if(token != null){
            if(jwtUtil.validateToken(token)){
                claims = jwtUtil.getUserInfoFromToken(token);
            }else{
                throw new IllegalArgumentException("Token Error");
            }

            boardRepository.save(new Board(boardRequestDto, claims.getSubject()));
            return "성공";
        }
        return "실패";
    }
}
