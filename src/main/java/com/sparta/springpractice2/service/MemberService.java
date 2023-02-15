package com.sparta.springpractice2.service;

import com.sparta.springpractice2.dto.MemberRequestDto;
import com.sparta.springpractice2.entity.Member;
import com.sparta.springpractice2.jwt.JwtUtil;
import com.sparta.springpractice2.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;


@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public String saveMember(MemberRequestDto memberRequestDto) {
        if(memberRepository.existsByUsername(memberRequestDto.getUsername())){
            return "이미 존재하는 id 입니다.";
        }
        memberRepository.save(new Member(memberRequestDto));
        return "회원가입 성공.";
    }

    @Transactional(readOnly = true)
    public String login(MemberRequestDto memberRequestDto, HttpServletResponse response) {
        Member member = memberRepository.findByUsername(memberRequestDto.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 회원입니다.")
        );

        if(!memberRequestDto.getPassword().equals(member.getPassword())){
            return "비밀번호가 다릅니다.";
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(member.getUsername(), member.getRole()));
        return "로그인 성공.";
    }

    public Member getMember(Claims claims) {
        Member member = memberRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("없는 회원입니다.")
        );
        return member;
    }
}
