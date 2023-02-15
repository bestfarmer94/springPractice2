package com.sparta.springpractice2.service;

import com.sparta.springpractice2.dto.BoardRequestDto;
import com.sparta.springpractice2.dto.BoardResponseDto;
import com.sparta.springpractice2.entity.Board;
import com.sparta.springpractice2.entity.Member;
import com.sparta.springpractice2.entity.MemberEnum;
import com.sparta.springpractice2.jwt.JwtUtil;
import com.sparta.springpractice2.repository.BoardRepository;
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
    private final MemberService memberService;

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
    public BoardResponseDto writeBoard(BoardRequestDto boardRequestDto, HttpServletRequest request) {
        Claims claims = jwtUtil.validToken(request);
        Member member = memberService.getMember(claims);

        Board board = boardRepository.save(new Board(boardRequestDto, member));
        return new BoardResponseDto(board);
    }

    @Transactional(readOnly = true)
    public BoardResponseDto selectBoard(Long id) {
        Board board = getBoard(id);
        return new BoardResponseDto(board);
    }

    @Transactional
    public BoardResponseDto updateBoard(Long boardId, BoardRequestDto boardRequestDto, HttpServletRequest request) {
        Claims claims = jwtUtil.validToken(request);
        Member member = memberService.getMember(claims);
        Board board = getBoard(boardId);
        confirmId(member, board);

        board.update(boardRequestDto);
        return new BoardResponseDto(board);
    }

    @Transactional
    public String deleteBoard(Long boardId, HttpServletRequest request) {
        Claims claims = jwtUtil.validToken(request);
        Member member = memberService.getMember(claims);
        Board board = getBoard(boardId);
        confirmId(member, board);

        boardRepository.delete(board);
        return "삭제 성공";
    }

    public Board getBoard(Long id) {
        Board board = boardRepository.findByBoardId(id).orElseThrow(
                () -> new IllegalArgumentException("없는 게시글 입니다.")
        );
        return board;
    }

    public void confirmId(Member member, Board board) {
        if (member.getRole() == MemberEnum.ADMIN || member.getUsername().equals(board.getMember().getUsername())) {
            return;
        }
        throw new IllegalArgumentException("해당 게시글은 회원님의 게시글이 아닙니다.");
    }
}
