package com.chat.kit.my_util.account.dto;

import com.chat.kit.my_util.account.entity.Account;
import com.chat.kit.my_util.account.enums.Gender;
import com.chat.kit.my_util.account.enums.Region;
import com.chat.kit.my_util.account.enums.Role;
import com.chat.kit.my_util.account.util.MemberPolicyProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class RegisterRequest {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Create {

        private String nickname;

        private String email;

        private String phoneNumber;

        private LocalDate birth;

        private Gender gender;

        private String name;

        private Region region;

        private String password;


        private String identityVerificationToken;

        @NotNull
        private Boolean terms1; //임시 추후 약관 확정되면 변경

        @NotNull
        private Boolean terms2; //임시 추후 약관 확정되면 변경


        @JsonIgnore
        public boolean isValidPassword() {
            return Pattern.matches(MemberPolicyProperties.PASSWORD_REGEX,
                    password);
        }


        public Account toAccount(PasswordEncoder passwordEncoder){
            return Account.builder()
                    .email(email)
                    .phoneNumber(phoneNumber)
                    .role(Role.ASSOCIATE)
                    .password(passwordEncoder.encode(password))
                    .region(region)
                    .term1(terms1)
                    .term2(terms2)
                    .build();
        }


    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SocialCreate {

        private String nickname;

        private String loginId; // 이메일 또는 전화번호

        private Region region;

        private String name;

        private String identityVerificationToken;

        private String invitationToken;

        @NotNull
        private Boolean terms1; //임시 추후 약관 확정되면 변경

        @NotNull
        private Boolean terms2; //임시 추후 약관 확정되면 변경
    }

}
