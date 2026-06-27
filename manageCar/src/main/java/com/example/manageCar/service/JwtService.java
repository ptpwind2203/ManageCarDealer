package com.example.manageCar.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    // Chuỗi secret key bắt buộc phải dài từ 32 ký tự trở lên (256-bit)
    private final String SECRET_KEY = "my_secret_manage_car_must_be_at_least_32_bytes_long";

    private Key getSigningKey() {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String account) {
        return Jwts.builder()
                .subject(account) // Ở bản mới dùng .subject() thay vì .setSubject()
                .issuedAt(new Date()) // .issuedAt() thay vì .setIssuedAt()
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getSigningKey()) // Ký trực tiếp bằng Key, không cần truyền SignatureAlgorithm nữa vì nó tự nhận diện
                .compact();
    }
}