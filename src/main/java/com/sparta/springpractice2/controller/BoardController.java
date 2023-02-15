package com.sparta.springpractice2.controller;

import com.sparta.springpractice2.dto.BoardRequestDto;
import com.sparta.springpractice2.dto.BoardResponseDto;
import com.sparta.springpractice2.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
            HttpServletRequest httpServletRequest) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }
        return ResponseEntity.ok(boardService.writeBoard(boardRequestDto, httpServletRequest));
    }

    @GetMapping("/board/{boardId}")
    public BoardResponseDto selectBoard(@PathVariable Long boardId) {
        return boardService.selectBoard(boardId);
    }

    @PutMapping("/board/{boardId}")
    public BoardResponseDto updateBoard(
            @PathVariable Long boardId,
            @RequestBody BoardRequestDto boardRequestDto,
            HttpServletRequest httpServletRequest) {

        return boardService.updateBoard(boardId, boardRequestDto, httpServletRequest);
    }

    @DeleteMapping("/board/{boardId}")
    public String deleteBoard(@PathVariable Long boardId, HttpServletRequest httpServletRequest) {
        return boardService.deleteBoard(boardId, httpServletRequest);
    }
}
