package com.example.demo.domain.account.type;

public enum AccountRoleType {
    PLAYER("ROLE_PLAYER"),
    SPONSOR("ROLE_SPONSOR"),
    ADMIN("ROLE_ADMIN");

    private final String authority;
    AccountRoleType(String authority) {
        this.authority = authority;
    }

    public String getAuthority(){
        return this.authority;
    }
}
