package com.myproject.spring_back.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Service
public class JwtService {
  private final Key key;
  
  public JwtService() {
    String secret = System.getenv("JWT_SECRET");
    if (secret == null || secret.trim().isEmpty()) {
      throw new IllegalStateException(
        "JWT_SECRET environment variable is required. " +
        "Please set it with a strong secret key (minimum 32 characters)."
      );
    }
    this.key = Keys.hmacShaKeyFor(secret.getBytes());
  }

  public String generate(String subject, Map<String, Object> claims) {
    Instant now = Instant.now();
    return Jwts.builder()
      .setSubject(subject)
      .addClaims(claims)
      .setIssuedAt(Date.from(now))
      .setExpiration(Date.from(now.plus(2, ChronoUnit.HOURS)))
      .signWith(key, SignatureAlgorithm.HS256)
      .compact();
  }

  public String getSubject(String token) {
    return Jwts.parserBuilder().setSigningKey(key).build()
      .parseClaimsJws(token).getBody().getSubject();
  }
}