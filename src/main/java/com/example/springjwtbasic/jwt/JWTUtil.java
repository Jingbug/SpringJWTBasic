package com.example.springjwtbasic.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {

    private SecretKey secretKey;

    // application.properties에서 생성한 String 타입의 Key를 가져와 SecretKey 객체 타입으로 변환
    public JWTUtil(@Value("${spring.jwt.secret}")String secret) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    // token의 특정 요소를 검증
    public String getUsername(String token) {
        // 우리 server에서 생성된 key로 생성한 username이 맞는지 확인
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    public String getRole(String token) {
        // 우리 server에서 생성된 key로 생성한 password가 맞는지 확인
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public Boolean isExpired(String token) {
        // 우리 server에서 생성된 token이 소멸되었는지 확인
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    public String createJWT(String username, String role, Long expireMs) {
        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                // token의 발행 시간
                .issuedAt(new Date(System.currentTimeMillis()))
                // token 소멸 시간
                .expiration(new Date(System.currentTimeMillis() + expireMs))
                // 암호화 진행
                .signWith(secretKey)
                .compact();
    }
}
