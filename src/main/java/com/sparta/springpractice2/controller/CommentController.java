package com.sparta.springpractice2.controller;

import com.sparta.springpractice2.dto.CommentRequestDto;
import com.sparta.springpractice2.dto.CommentResponseDto;
import com.sparta.springpractice2.security.UserDetailsImpl;
import com.sparta.springpractice2.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
            @AuthenticationPrincipal UserDetailsImpl userDetails){

        if(bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }
        return ResponseEntity.ok(commentService.writeComment(boardId, commentRequestDto, userDetails.getMember()));
    }
    @PostMapping("/comment/like/{commentId}")
    public String updateLikeComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.updateLikeComment(commentId, userDetails.getMember());
    }

    @PutMapping("/comment/{commentId}")
    public CommentResponseDto updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails){

        return commentService.updateComment(commentId, commentRequestDto, userDetails.getMember());
    }

    @DeleteMapping("/comment/{commentId}")
    public String deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.deleteComment(commentId, userDetails.getMember());
    }
}
