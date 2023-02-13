package com.sparta.springpractice2.controller;

import com.sparta.springpractice2.dto.BoardResponseDto;
import com.sparta.springpractice2.dto.MemberDto;
import com.sparta.springpractice2.service.BoardService;
import com.sparta.springpractice2.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/saveMember")
    @ResponseBody
    public ResponseEntity saveMember(@Valid @RequestBody MemberDto memberDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }
        return ResponseEntity.ok(memberService.saveMember(memberDto));
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity login(@RequestBody MemberDto memberDto, HttpServletResponse httpServletResponse) {

        return ResponseEntity.ok(memberService.login(memberDto, httpServletResponse));
    }
}
