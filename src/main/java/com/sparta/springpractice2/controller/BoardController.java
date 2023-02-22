package com.sparta.springpractice2.controller;

import com.sparta.springpractice2.dto.BoardRequestDto;
import com.sparta.springpractice2.dto.BoardResponseDto;
import com.sparta.springpractice2.security.UserDetailsImpl;
import com.sparta.springpractice2.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/main")
    public List<BoardResponseDto> listAll() {
        return boardService.listAll();
    }

    @PostMapping("/board")
    public ResponseEntity writeBoard(
            @Valid @RequestBody BoardRequestDto boardRequestDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }
        return ResponseEntity.ok(boardService.writeBoard(boardRequestDto, userDetails.getMember()));
    }

    @GetMapping("/board/{boardId}")
    public BoardResponseDto selectBoard(@PathVariable Long boardId) {
        return boardService.selectBoard(boardId);
    }

    @PostMapping("/board/like/{boardId}")
    public String updateLikeBoard(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return boardService.updateLikeBoard(boardId, userDetails.getMember());
    }

    @PutMapping("/board/{boardId}")
    public BoardResponseDto updateBoard(
            @PathVariable Long boardId,
            @RequestBody BoardRequestDto boardRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return boardService.updateBoard(boardId, boardRequestDto, userDetails.getMember());
    }



    @DeleteMapping("/board/{boardId}")
    public String deleteBoard(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return boardService.deleteBoard(boardId, userDetails.getMember());
    }
}
