package com.example.shoppingmall.security;

public enum MemberRoleEnum {

    USER(Authority.USER),
    ADMIN(Authority.ADMIN);


    private final String authority;

    MemberRoleEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String USER = "ROLE_USER";
        public static final String ADMIN = "ROLE_ADMIN";

    }

    public static MemberRoleEnum fromString(String membertype) {
        switch (membertype) {
            case "ROLE_USER":
                return USER;
            case "ROLE_ADMIN":
                return ADMIN;

        }

        return null;
    }
}
