package com.chat.kit.my_util.account.enums;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public enum Role implements  GrantedAuthority {
    ANONYMOUS("비회원"),
    ASSOCIATE("준회원"),
    MEMBER("회원"),
    ADMIN("관리자");

    private final String description;
    public String getName() {
        return name();
    }

    public String getDescription() {
        return this.description;
    }

    public String getAuthority() {
        return name();
    }
}