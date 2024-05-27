package com.example.shoppingmall.dto;

import lombok.Getter;

@Getter
public class LoginDAO {

    private String userid;
    private String password;

    public LoginDAO(String userID, String password) {
        this.userid = userID;
        this.password = password;
    }


}
