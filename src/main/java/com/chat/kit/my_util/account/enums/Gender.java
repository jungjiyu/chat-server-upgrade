package com.chat.kit.my_util.account.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Gender {
    MALE("남성"),
    FEMALE("여성");

    private final String description;

    public String getName() {
        return name();
    }

    public String getDescription() {
        return description;
    }
}
