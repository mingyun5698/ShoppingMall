package com.example.DWShopProject.controller;


import com.example.DWShopProject.dao.MemberDto;
import com.example.DWShopProject.entity.Member;
import com.example.DWShopProject.service.EmailService;
import com.example.DWShopProject.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Random;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class EmailController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private EmailService emailService;

    //회원가입시 이메일 인증번호 발송
    @PostMapping("/email")
    public int emailAuth(@RequestBody String email) {
        Random random = new Random();
        int verificationCode = random.nextInt(888888) + 111111;

        emailService.sendVerificationCode(email, verificationCode); // 인증번호 메일보내기

        return verificationCode;
    }


    //이메일로 아이디 찾기
    @PostMapping("/findid")
    ResponseEntity<List<Member>> findMemberByEmail(@RequestBody MemberDto memberDto) {
        List<Member> members = memberService.findMembersByEmail(memberDto);

        if (!members.isEmpty()) {
            emailService.sendEmailWithMemberIds(memberDto.getEmail(), members); //아이디 메일보내기
        }

        return ResponseEntity.ok(members);
    }



}
