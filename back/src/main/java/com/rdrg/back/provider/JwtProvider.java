package com.rdrg.back.provider;

import com.nimbusds.jose.util.StandardCharset;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtProvider {
    
    @Value("${jwt.secret-key}")
    private String secretKey;

    public String create(String userId) {
        Date expiredDate = Date.from(Instant.now().plus(10, ChronoUnit.HOURS));
        String jwt = null;

        try {
            Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharset.UTF_8));

            jwt = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .compact();
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
        return jwt;
    }

    public String validate (String jwt) {

        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharset.UTF_8));
        String userId = null;

        try {
            userId = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
        return userId;
    }
}
