package com.chat.kit.my_util.account.enums;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * SuccessCode 구조
 * 3자리로 구성된 코드
 * XXX
 * 앞 세자리 : HttpStatus 코드
 */
@AllArgsConstructor
public enum SuccessCode  {
    OK(HttpStatus.OK, HttpStatus.OK.name()),
    Deleted(HttpStatus.OK, "Deleted"),
    Modified(HttpStatus.OK, "Modified"),
    CREATED(HttpStatus.CREATED, HttpStatus.CREATED.name());

    private HttpStatus httpStatus;
    private String message;

    public Integer getCode() {
        return httpStatus.value();
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return this.getCode().toString();
    }

    public String getDescription() {
        return this.getMessage();
    }
}
