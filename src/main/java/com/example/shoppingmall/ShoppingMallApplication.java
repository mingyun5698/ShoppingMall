package com.example.shoppingmall;

import com.example.shoppingmall.entity.Member;
import com.example.shoppingmall.repository.MemberRepository;
import com.example.shoppingmall.security.MemberRoleEnum;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShoppingMallApplication implements CommandLineRunner {

    private final MemberRepository memberRepository;

    public ShoppingMallApplication(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ShoppingMallApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        memberRepository.save(new Member(1L, MemberRoleEnum.ADMIN, "ADMIN", "aaa", "ADMIN", "dqwqdw", "dqwqdw", "dqwqdw", "dqwqdw", "dqwqdw"));
        memberRepository.save(new Member(2L, MemberRoleEnum.USER, "USER", "bbb", "USER", "dqwqdw", "dqwqdw", "dqwqdw", "dqwqdw", "dqwqdw"));
        memberRepository.save(new Member(3L, MemberRoleEnum.USER, "ccc", "ccc", "ccc", "dqwqdw", "dqwqdw", "dqwqdw", "dqwqdw", "dqwqdw"));
        memberRepository.save(new Member(4L, MemberRoleEnum.USER, "ddd", "ddd", "ddd", "dqwqdw", "dqwqdw", "dqwqdw", "dqwqdw", "dqwqdw"));
        memberRepository.save(new Member(5L, MemberRoleEnum.USER, "eee", "eee", "eee", "dqwqdw", "dqwqdw", "dqwqdw", "dqwqdw", "dqwqdw"));


    }
}
