package com.sparta.springpractice2.controller;

import com.sparta.springpractice2.dto.CommentRequestDto;
import com.sparta.springpractice2.dto.CommentResponseDto;
import com.sparta.springpractice2.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment/{boardId}")
    public ResponseEntity writeComment(
            @PathVariable Long boardId,
            @Valid @RequestBody CommentRequestDto commentRequestDto,
            BindingResult bindingResult,
            HttpServletRequest httpServletRequest){

        if(bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }
        return ResponseEntity.ok(commentService.writeComment(boardId, commentRequestDto, httpServletRequest));
    }

    @PutMapping("/comment/{commentId}")
    public CommentResponseDto updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto commentRequestDto,
            HttpServletRequest httpServletRequest){

        return commentService.updateComment(commentId, commentRequestDto, httpServletRequest);
    }

    @DeleteMapping("/comment/{commentId}")
    public String deleteComment(@PathVariable Long commentId, HttpServletRequest httpServletRequest){
        return commentService.deleteComment(commentId, httpServletRequest);
    }
}
