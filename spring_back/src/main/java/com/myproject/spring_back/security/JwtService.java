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
  private final Key key = Keys.hmacShaKeyFor(
      System.getenv().getOrDefault("JWT_SECRET",
      "xb6634ntrd67nt47cty78bn65c+-*4-t").getBytes()
  );

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