package com.vipul.springSecurity.security;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private final String SECRET = "jhashaj34jklda9adk9pa92jjb3kbhjakhsjii99adsk9wjb9bjbas9999asdjk99999asjbkjb232jbjkbads8baksjdbkbaksj";
    private final Key key = new javax.crypto.spec.SecretKeySpec(SECRET.getBytes(), "HmacSHA256");

    private final long ACCESS_TOKEN_VALIDITY = 15 * 60 * 1000; // 15 min
    private final long REFRESH_TOKEN_VALIDITY = 7 * 24 * 60 * 60 * 1000; // 7 days

    public String generateAccessToken(Long mobile, Long userId) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("mobile", mobile);
        return Jwts.builder()
                .setSubject(userId.toString())
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(Long mobile, Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("mobile", mobile);
        return Jwts.builder()
                .setSubject(userId.toString())
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims validateToken(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            e.printStackTrace();
            throw new InvalidDataAccessApiUsageException("Invalid api exception");
        }
    }
}
