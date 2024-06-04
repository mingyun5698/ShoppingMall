package com.example.shoppingmall.entity;

import com.example.shoppingmall.security.MemberRoleEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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


    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"member"})
    private List<Board> boards;


}
