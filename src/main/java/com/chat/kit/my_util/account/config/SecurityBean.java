package com.chat.kit.my_util.account.config;

import com.chat.kit.my_util.account.util.JwtDecoder;
import com.chat.kit.my_util.account.util.JwtProvider;
import com.chat.kit.my_util.account.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Slf4j
@Configuration
public class SecurityBean {

    private KeyPair keyPair;


    public SecurityBean(
            @Value("${keys.private-key-path}") Resource privateKeyResource,
            @Value("${keys.public-key-path}") Resource publicKeyResource
    ) throws NoSuchAlgorithmException {
        KeyPair tempKeyPair = null;

        try {
            // Resource 객체로부터 InputStream을 사용하여 파일 내용 읽기
            RSAPublicKey rsaPublicKey = readX509PublicKey(publicKeyResource);
            RSAPrivateKey rsaPrivateKey = readPKCS8PrivateKey(privateKeyResource);
            tempKeyPair = new KeyPair(rsaPublicKey, rsaPrivateKey);
            log.info("Key File Loaded");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Failed to Load Key Files");
            System.exit(1);
        }

        if (tempKeyPair == null) {
            log.error("Key not initialized");
            System.exit(1);
        }
        this.keyPair = tempKeyPair;
    }


    public static RSAPublicKey readX509PublicKey(Resource resource) throws Exception {
        String key = new String(Files.readAllBytes(resource.getFile().toPath()), Charset.defaultCharset());

        String publicKeyPEM = key
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PUBLIC KEY-----", "");

        byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    public RSAPrivateKey readPKCS8PrivateKey(Resource resource) throws Exception {
        String key = new String(Files.readAllBytes(resource.getFile().toPath()), Charset.defaultCharset());

        String privateKeyPEM = key
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PRIVATE KEY-----", "");

        byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }




    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtProvider jwtTokenProvider() {
        return new JwtProvider(keyPair.getPrivate());
    }

    @Bean
    public JwtDecoder jwtTokenValidator() {
        return new JwtDecoder(keyPair.getPublic());
    }

    @Bean
    public JwtUtils jwtUtils() {
        return new JwtUtils();
    }


}
