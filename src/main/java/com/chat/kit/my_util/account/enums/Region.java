package com.chat.kit.my_util.account.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Region{

    KR("한국인"),
    OTHER("외국인");

    private final String description;

    public String getName() {
        return this.name();
    }

    public String getDescription() {
        return this.description;
    }
}
