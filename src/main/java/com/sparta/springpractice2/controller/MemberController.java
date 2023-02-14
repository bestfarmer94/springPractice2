package com.sparta.springpractice2.controller;

import com.sparta.springpractice2.dto.MemberRequestDto;
import com.sparta.springpractice2.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/saveMember")
    public ResponseEntity saveMember(@Valid @RequestBody MemberRequestDto memberRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }
        return ResponseEntity.ok(memberService.saveMember(memberRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody MemberRequestDto memberRequestDto, HttpServletResponse httpServletResponse) {
        return ResponseEntity.ok(memberService.login(memberRequestDto, httpServletResponse));
    }
}
