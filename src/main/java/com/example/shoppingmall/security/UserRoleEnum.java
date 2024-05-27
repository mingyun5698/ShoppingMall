package com.example.shoppingmall.security;

public enum UserRoleEnum {

    USER(Authority.USER),
    PARKMANAGER(Authority.PARKMANAGER),
    SECURITYOFFICER(Authority.SECURITYOFFICER),
    VETERINARIAN(Authority.VETERINARIAN),
    GUIDE(Authority.GUIDE),
    ZOOKEEPER(Authority.ZOOKEEPER);

    private final String authority;

    UserRoleEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String USER = "ROLE_USER";
        public static final String PARKMANAGER = "ROLE_PARKMANAGER";
        public static final String SECURITYOFFICER = "ROLE_SECURITYOFFICER";
        public static final String VETERINARIAN = "ROLE_VETERINARIAN";
        public static final String GUIDE = "ROLE_GUIDE";
        public static final String ZOOKEEPER = "ROLE_ZOOKEEPER";
    }

    public static UserRoleEnum fromString(String usertype) {
        switch (usertype) {
            case "ROLE_USER":
                return USER;
            case "ROLE_PARKMANAGER":
                return PARKMANAGER;
            case "ROLE_SECURITYOFFICER":
                return SECURITYOFFICER;
            case"ROLE_VETERINARIAN":
                return VETERINARIAN;
            case"ROLE_GUIDE":
                return GUIDE;
            case"ROLE_ZOOKEEPER":
                return ZOOKEEPER;
        }

        return null;
    }
}
