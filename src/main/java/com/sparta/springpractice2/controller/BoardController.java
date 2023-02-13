package com.sparta.springpractice2.controller;

import com.sparta.springpractice2.dto.BoardRequestDto;
import com.sparta.springpractice2.dto.BoardResponseDto;
import com.sparta.springpractice2.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public String writeBoard(@RequestBody BoardRequestDto boardRequestDto, HttpServletRequest httpServletRequest){
        return boardService.writeBoard(boardRequestDto, httpServletRequest);
    }
}
