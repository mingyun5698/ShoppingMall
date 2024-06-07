package com.example.shoppingmall.controller;
import com.example.shoppingmall.dto.LoginDto;
import com.example.shoppingmall.dto.MemberDto;
import com.example.shoppingmall.entity.Member;
import com.example.shoppingmall.repository.MemberRepository;
import com.example.shoppingmall.security.MemberDetailsImpl;
import com.example.shoppingmall.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MemberRestController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    public MemberRestController(MemberService memberService, MemberRepository memberRepository) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
    }

    /*회원가입 API*/
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody MemberDto memberDto) {
        try {
            Member member = memberService.signUp(memberDto);
            return ResponseEntity.ok(member);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("회원가입 중 오류가 발생했습니다. 다시 시도해주세요.");
        }
    }

    // 모든 사용자 뷰 API
    @GetMapping("/members")
    public ResponseEntity<List<Member>> getAllMembers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            List<Member> members = (List<Member>) memberRepository.findAll();
            return ResponseEntity.ok(members);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    /* 관리자 전용 회원 추가 API */
    @PostMapping("/members")
    public ResponseEntity<Member> addMember(@RequestBody MemberDto memberDto) {
            Member member = memberService.signUp(memberDto);
            return ResponseEntity.ok(member);

    }

    // 관리자 전용 회원 삭제 API
    @DeleteMapping("/members/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable Long id) {
        try {
            memberService.deleteMember(id);
            return ResponseEntity.ok("사용자가 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("사용자 삭제 중 오류가 발생했습니다. 다시 시도해주세요.");
        }
    }

    // 관리자 전용 회원 수정 API
    @PutMapping("/members/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody MemberDto memberDto) {
        Member member = memberService.updateMember(id, memberDto);
        if (member != null) {
            return ResponseEntity.ok(member);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /* 마이페이지 조회 API */
    @GetMapping("/mypage")
    public ResponseEntity<?> getMyPage(@AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        try {
            if (memberDetails == null || memberDetails.getMember() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증되지 않은 사용자입니다.");
            }

            Member member = memberDetails.getMember();
            return ResponseEntity.ok(member);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }
    }

    // mypage 회원탈퇴 API 및 header 쿠키삭제
    @DeleteMapping("/mypage")
    public ResponseEntity<String> deleteMyMember(@AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        memberService.deleteMember(memberDetails.getMember().getId());
        return ResponseEntity.ok("사용자 계정이 성공적으로 삭제되었습니다.");
    }

    // mypage 회원 수정 API
    @PutMapping("/mypage")
    public ResponseEntity<Member> updateMyMember(@RequestBody MemberDto memberDto,
                                               @AuthenticationPrincipal MemberDetailsImpl memberDetails) {


        Member currentMember = memberService.updateMember(memberDetails.getMember().getId(), memberDto);

        return ResponseEntity.ok(currentMember);
    }


    // 로그인 api
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDao, HttpServletResponse response) {

            String token = memberService.login(loginDao, response); // 로그인 서비스에서 토큰 생성 및 반환
            return ResponseEntity.ok(token); // 클라이언트에게 토큰을 반환

    }


    //관리자에게 텍스트 보내기
    @GetMapping("/adminButton")
    public ResponseEntity<String> adminButton() {

            String message = "관리자 버튼";
            return new ResponseEntity<>(message, HttpStatus.OK);

    }
}
