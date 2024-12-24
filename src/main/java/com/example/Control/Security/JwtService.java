package com.example.Control.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    final private String jwtKey = System.getenv("JWT_SECRET");

    public Integer extractId(String token) {
        return extractAllClaims(token).get("id", Integer.class);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    private Key getSigningKey() {
        return new SecretKeySpec(jwtKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    public boolean isTokenValid(String token, Integer userId) {
        Integer parsedId = extractId(token);
        return (parsedId.equals(userId)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public String generateToken(Map<String, Object> fields, UserDetails userDetails) {
        return Jwts.builder()
                .claims(fields)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
