package com.chat.kit.my_util.account.util;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class EmailVerificationContextImpl implements EmailVerificationContext {

    private final Map<String, VerificationData> verificationContext;

    public void setValue(String key, String value) {
        VerificationData verificationData = VerificationData.builder()
                .value(value)
                .sendTime(System.currentTimeMillis())
                .attempts(0)
                .build();
        verificationContext.put(key, verificationData);
    }

    @Override
    public VerificationData get(String key) {
        return verificationContext.get(key);
    }

    @Override
    public void remove(String key) {
        verificationContext.remove(key);
    }
}
