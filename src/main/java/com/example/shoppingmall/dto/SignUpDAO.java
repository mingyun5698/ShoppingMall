package com.example.shoppingmall.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class SignUpDAO {
    private Long id;
    private String usertype;
    private String username;
    private String userId;
    private String password;
    private String birthdate;
    private String gender;
    private String email;
    private String contact;
    private String address;


}
