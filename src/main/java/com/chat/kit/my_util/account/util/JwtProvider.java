package com.chat.kit.my_util.account.util;

import com.chat.kit.my_util.account.enums.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.security.PrivateKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@RequiredArgsConstructor
public class JwtProvider {

    private final PrivateKey privateKey;

    public String createAccessToken(AbstractAuthenticationToken token) {

        UserDetailsImpl principal = (UserDetailsImpl) token.getPrincipal();

        return createAccessToken(principal.getUsername(), principal.getNickname(), principal.getRole(), principal.getMember().getId());
    }

//	public String changeRefreshToken(String category, String username,String nickname, Role role) {
//		return category.equals(JwtProperties.LARGE_REFRESH_TOKEN) ? createRememberMeRefreshToken(username, nickname, role) : createRefreshToken(username, nickname, role);
//	}

    public String createSystemToken(){
        Instant instant = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant expiredDate = instant.plus(JwtProperties.ACCESS_TOKEN_EXPIRE_TIME, ChronoUnit.SECONDS);

        return Jwts.builder()
                .setSubject("SYSTEM")
                .claim(JwtProperties.ROLE, "SYSTEM")
                .setIssuedAt(Date.from(instant))
                .setExpiration(Date.from(expiredDate))
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }

    //JWT 토큰 생성
    public String createJwt(String category, String username, String nickname, Role role, Long expiredMs, Long memberId) {
        //username은 email or phoneNumber

        Instant instant = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant expiredDate = instant.plus(expiredMs, ChronoUnit.SECONDS);

        return Jwts.builder()
                .setSubject(username)
                .claim(JwtProperties.MEMBER_ID, memberId)
                .claim(JwtProperties.NICK_NAME, nickname)
                .claim(JwtProperties.CATEGORY, category)
                .claim(JwtProperties.ROLE, role.getName())
                .setIssuedAt(Date.from(instant))
                .setExpiration(Date.from(expiredDate))
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }

    public String createAccessToken(String username, String nickname, Role role, Long memberId) {
        return createJwt(JwtProperties.ACCESS_TOKEN, username, nickname, role, JwtProperties.ACCESS_TOKEN_EXPIRE_TIME, memberId);
    }

    public String createRefreshToken(String username, String nickname, Role role, Long memberId) {
        return createJwt(JwtProperties.REFRESH_TOKEN, username, nickname, role, JwtProperties.REFRESH_TOKEN_EXPIRE_TIME, memberId);
    }

    public String createRememberMeRefreshToken(String username, String nickname, Role role, Long memberId) {
        return createJwt(JwtProperties.LARGE_REFRESH_TOKEN, username, nickname, role, JwtProperties.REMEMBER_ME_REFRESH_TOKEN_EXPIRE_TIME, memberId);
    }

    public String createAccessToken(UserDetailsImpl userDetails) {
        return createJwt(JwtProperties.ACCESS_TOKEN, userDetails.getUsername(), userDetails.getNickname(), userDetails.getRole(), JwtProperties.ACCESS_TOKEN_EXPIRE_TIME, userDetails.getMember().getId());
    }

    public String createRefreshToken(UserDetailsImpl userDetails, boolean isLong) {
        return createJwt(JwtProperties.REFRESH_TOKEN, userDetails.getUsername(), userDetails.getNickname(), userDetails.getRole(), isLong ? JwtProperties.REFRESH_TOKEN_EXPIRE_TIME : JwtProperties.REMEMBER_ME_REFRESH_TOKEN_EXPIRE_TIME, userDetails.getMember().getId());
    }

}
