package com.chat.kit.my_util.account.config;

import com.chat.kit.my_util.account.util.EmailVerificationContext;
import com.chat.kit.my_util.account.util.EmailVerificationContextImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class AuthConfig {


    @Bean
    public EmailVerificationContext verificationContext() {
        return new EmailVerificationContextImpl(new HashMap<>());
    }
}
