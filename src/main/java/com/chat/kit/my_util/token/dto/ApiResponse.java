package com.chat.kit.my_util.token.dto;

import com.chat.kit.api.response.common.error.ErrorCode;
import com.chat.kit.my_util.token.enums.SuccessCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {
    @JsonProperty("isSuccess")
    private boolean isSuccess;
    private Integer code;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(SuccessCode code, T data) {

        return ApiResponse.<T>builder()
                .isSuccess(true)
                .code(code.getCode())
                .message(code.getMessage())
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> error(ErrorCode code) {

        return ApiResponse.<T>builder()
                .isSuccess(false)
                .code(code.getCode())
                .message(code.getMessage())
                .build();
    }
    public static <T> ApiResponse<T> error(ErrorCode code, String message) {

        return ApiResponse.<T>builder()
                .isSuccess(false)
                .code(code.getCode())
                .message(message)
                .build();
    }
}
