package com.chat.kit.my_util.account.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class RegisterResponse {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Success{
        private String email;
        private String phone;
        private String nickname;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NicknameDuplication{
        private String nickname;

        @JsonProperty("isDuplication")
        private Boolean isDuplication;
    }

}
