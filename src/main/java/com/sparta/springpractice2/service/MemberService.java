package com.sparta.springpractice2.service;

import com.sparta.springpractice2.dto.MemberRequestDto;
import com.sparta.springpractice2.entity.Member;
import com.sparta.springpractice2.entity.MemberEnum;
import com.sparta.springpractice2.jwt.JwtUtil;
import com.sparta.springpractice2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String saveMember(MemberRequestDto memberRequestDto) {
        String username = memberRequestDto.getUsername();
        String password = passwordEncoder.encode(memberRequestDto.getPassword());
        MemberEnum role = memberRequestDto.getRole();

        if(memberRepository.existsByUsername(memberRequestDto.getUsername())){
            return "이미 존재하는 id 입니다.";
        }
        memberRepository.save(new Member(username, password, role));
        return "회원가입 성공.";
    }

    @Transactional(readOnly = true)
    public String login(MemberRequestDto memberRequestDto, HttpServletResponse response) {
        String username = memberRequestDto.getUsername();
        String password = memberRequestDto.getPassword();

        Member member = getMember(username);

        if(!passwordEncoder.matches(password, member.getPassword())){
            return "비밀번호가 다릅니다.";
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(username, member.getRole()));
        return "로그인 성공.";
    }

    public Member getMember(String username) {
        Member member = memberRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("없는 회원입니다.")
        );
        return member;
    }
}
