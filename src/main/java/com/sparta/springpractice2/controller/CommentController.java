package com.sparta.springpractice2.controller;

import com.sparta.springpractice2.dto.CommentRequestDto;
import com.sparta.springpractice2.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment/{boardId}")
    public String writeComment(@PathVariable Long boardId, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest httpServletRequest){
        return commentService.writeComment(boardId, commentRequestDto, httpServletRequest);
    }
}
