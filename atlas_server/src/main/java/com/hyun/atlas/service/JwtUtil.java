package com.hyun.atlas.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationTime;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String userCode) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userCode);

        Instant now = Instant.now();
        return Jwts.builder()
            .claims(claims)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(expirationTime, ChronoUnit.MILLIS)))
            .signWith(getSigningKey(), Jwts.SIG.HS512)
            .compact();
    }

    public String extractUserCode(String token) {
        return getClaims(token).getSubject();
    }

    public boolean validateToken(String token, String userCode) {
        try {
            Claims claims = getClaims(token);
            String extractedUserCode = claims.getSubject();
            Instant expiration = claims.getExpiration().toInstant();
            return extractedUserCode.equals(userCode) && !expiration.isBefore(Instant.now());
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
}