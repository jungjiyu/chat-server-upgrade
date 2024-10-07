package com.chat.kit.my_util.account.util;

import com.chat.kit.my_util.account.enums.Role;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;


import io.jsonwebtoken.Claims;


@RequiredArgsConstructor
public class JwtDecoder {
    private final PublicKey publicKey;

    // JWT의 앞부분에 추가된 접두사를 제거하여 실제 토큰을 반환하는 메서드
    public String detach(String token) {
        if (StringUtils.hasText(token) && token.startsWith(JwtProperties.TOKEN_PREFIX)) {
            return token.substring(JwtProperties.BEGIN_INDEX);
        }
        return token;
    }

    // 토큰에서 NickName 클레임을 가져오는 메서드
    public String getNickName(String token) {
        return parseClaims(token).get(JwtProperties.NICK_NAME, String.class);
    }

    // 토큰에서 Subject(사용자 이름)를 가져오는 메서드
    public String getUserName(String token) {
        return parseClaims(token).getSubject();
    }

    // 토큰에서 Role 클레임을 가져오는 메서드
    public Role getRole(String token) {
        return Role.valueOf(parseClaims(token).get(JwtProperties.ROLE, String.class));
    }

    // 토큰의 만료 여부를 확인하는 메서드
    public Boolean isExpired(String token) {
        Date expirationDate = parseClaims(token).getExpiration();
        return expirationDate.before(Date.from(LocalDateTime.now().atZone(TimeZone.getDefault().toZoneId()).toInstant()));
    }

    // 토큰에서 Category 클레임을 가져오는 메서드
    public String getCategory(String token) {
        return parseClaims(token).get(JwtProperties.CATEGORY, String.class);
    }

    // Refresh Token 유효성을 확인하는 메서드
    public void validRefreshToken(String token) {
        String category = getCategory(token);
        if (!category.equals(JwtProperties.REFRESH_TOKEN)) {
            throw new RuntimeException("Invalid token error");
        }
    }

    // 토큰에서 Member ID를 가져오는 메서드
    public Long getMemberId(String token) {
        return parseClaims(token).get(JwtProperties.MEMBER_ID, Long.class);
    }

    // JWT 토큰을 파싱하고 클레임을 가져오는 메서드 (공통 메서드)
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(publicKey)  // 서명 검증을 위한 공개 키 설정
                .parseClaimsJws(token)     // JWT 토큰 파싱 및 검증
                .getBody();                // 클레임 반환
    }
}
