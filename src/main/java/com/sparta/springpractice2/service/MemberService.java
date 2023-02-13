package com.sparta.springpractice2.service;

import com.sparta.springpractice2.dto.MemberDto;
import com.sparta.springpractice2.entity.Member;
import com.sparta.springpractice2.jwt.JwtUtil;
import com.sparta.springpractice2.repository.MemberRepository;
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
    public String saveMember(MemberDto memberDto) {
        if(memberRepository.existsByUsername(memberDto.getUsername())){
            return "이미 존재하는 id 입니다.";
        }
        memberRepository.save(new Member(memberDto));
        return "회원가입 성공.";
    }

    @Transactional(readOnly = true)
    public String login(MemberDto memberDto, HttpServletResponse response) {
        Member member = memberRepository.findByUsername(memberDto.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 회원입니다.")
        );

        if(!memberDto.getPassword().equals(member.getPassword())){
            return "비밀번호가 다릅니다.";
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(member.getUsername()));
        return "로그인 성공.";
    }


}
