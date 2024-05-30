package com.example.shoppingmall.service;

import com.example.shoppingmall.dto.LoginDto;
import com.example.shoppingmall.dto.MemberDto;
import com.example.shoppingmall.entity.Member;
import com.example.shoppingmall.jwt.JwtUtil;
import com.example.shoppingmall.repository.MemberRepository;
import com.example.shoppingmall.security.MemberRoleEnum;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class MemberService {

    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final JwtUtil jwtUtil;
    @Autowired
    private final PasswordEncoder passwordEncoder;



    // 회원가입
    public Member signUp(MemberDto memberDto) {
        Long id = null;
        MemberRoleEnum membertype = MemberRoleEnum.valueOf(memberDto.getMembertype());
        String memberId = memberDto.getMemberId();;
        String membername = memberDto.getMembername();
        String password = (memberDto.getPassword());
        String birthdate = memberDto.getBirthdate();
        String gender = memberDto.getGender();
        String email = memberDto.getEmail();
        String contact = memberDto.getContact();
        String address = memberDto.getAddress();

        if (memberRepository.findByMemberId(memberId).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 회원 이름입니다: " + memberId);
        }

        Member member = new Member(id, membertype, memberId, membername, password, birthdate, gender, email, contact, address);
        memberRepository.save(member);

        return member;
    }

    // 유저 삭제
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    // 유저 수정
    public Member findById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    // mypage 수정 완료
    public Member edit(MemberDto memberDto) {
        // MemberDTO를 Member 객체로 변환
        Member member = new Member();
        member.setId(memberDto.getId());
        member.setMemberId(memberDto.getMemberId());
        member.setPassword(memberDto.getPassword());
        member.setMembertype(MemberRoleEnum.valueOf(memberDto.getMembertype()));
        member.setMembername(memberDto.getMembername());
        member.setAddress(memberDto.getAddress());
        member.setContact(memberDto.getContact());
        member.setEmail(memberDto.getEmail());
        member.setBirthdate(memberDto.getBirthdate());
        return member;

        // 여기서 해당 사용자를 수정하는 로직을 구현
        // 예를 들어, JPA를 사용한다면 해당 사용자를 조회하고 값을 업데이트할 것입니다.
    }

    // MemberDTO를 Member 객체로 변환하는 메서드
    private Member convertToMember(MemberDto memberDto) {
        Member member = new Member();
        member.setId(memberDto.getId());
        member.setMemberId(memberDto.getMemberId());
        member.setPassword(memberDto.getPassword());
        member.setMembertype(MemberRoleEnum.valueOf(memberDto.getMembertype()));
        member.setMembername(memberDto.getMembername());
        member.setAddress(memberDto.getAddress());
        member.setContact(memberDto.getContact());
        member.setEmail(memberDto.getEmail());
        member.setBirthdate(memberDto.getBirthdate());

        return member;
    }


    // 관리자 수정 완료
    public Member updateMember(Long id, MemberDto memberDto) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            member.setMemberId(memberDto.getMemberId());
            member.setMembername(memberDto.getMembername());
            member.setPassword(memberDto.getPassword());
            member.setAddress(memberDto.getAddress());
            member.setBirthdate(memberDto.getBirthdate());
            member.setContact(memberDto.getContact());
            member.setMembertype(MemberRoleEnum.valueOf(memberDto.getMembertype()));
            member.setGender(memberDto.getGender());

            memberRepository.save(member);
            return member;
        } else {
            return null;
        }
    }


    /*로그인*/
//    @Transactional
//    public void login(LoginDto loginDao, HttpServletResponse response) {
//        Optional<Member> optionalMember = memberRepository.findByMemberId(loginDao.getMemberId());
//
//        if (optionalMember.isEmpty()) {
//            throw new IllegalArgumentException("회원이 존재하지 않음");
//        }
//
//        Member member = optionalMember.get();
//
//        // 쿠키에 토큰 저장
//        Cookie cookie = new Cookie("token", jwtUtil.createToken(member.getMemberId(), member.getMembertype()));
//        cookie.setMaxAge(7 * 24 * 60 * 60); // 7일 동안 유효
//        cookie.setPath("/");
//        cookie.setDomain("localhost");
//        cookie.setSecure(false);
//        response.addCookie(cookie);
//    }

    public String login(LoginDto loginDao, HttpServletResponse response) {
        Optional<Member> optionalMember = memberRepository.findByMemberId(loginDao.getMemberId());

        if (optionalMember.isEmpty()) {
            throw new IllegalArgumentException("회원이 존재하지 않음");
        }

        Member member = optionalMember.get();

        // JWT 토큰 생성
        String token = jwtUtil.createToken(member.getMemberId(), member.getMembertype());



        return token; // 토큰 반환
    }

}
