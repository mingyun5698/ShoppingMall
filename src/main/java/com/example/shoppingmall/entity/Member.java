package com.example.shoppingmall.entity;

import com.example.shoppingmall.security.MemberRoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MemberRoleEnum membertype;

    private String memberId;
    private String membername;
    private String password;
    private String birthdate;
    private String gender;
    private String email;
    private String contact;
    private String address;



}
