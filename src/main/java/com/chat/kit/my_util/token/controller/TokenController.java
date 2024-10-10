package com.chat.kit.my_util.token.controller;


import com.chat.kit.my_util.token.dto.TokenResponse;
import com.chat.kit.my_util.token.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {


    private final TokenService tokenService;

    @PostMapping("/system")
    public ResponseEntity<TokenResponse.Success> createSystemToken() {
        return ResponseEntity
                .status(200)
                .body(TokenResponse.Success
                .builder()
                .token(tokenService.createSystemToken())
                .build() );
    }

}
