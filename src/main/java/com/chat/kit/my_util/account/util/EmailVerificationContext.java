package com.chat.kit.my_util.account.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface EmailVerificationContext {
    void setValue(String key, String value);

    VerificationData get(String key);

    void remove(String key);

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class VerificationData {
        private String value; // 인증 코드
        private long sendTime; // 전송 시간
        private int attempts; // 시도 횟수

        public void incrementAttempts() {
            this.attempts++;
        }

    }
}