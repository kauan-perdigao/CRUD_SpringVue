package com.myproject.spring_back.security;

import com.myproject.spring_back.repository.UserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
  private final JwtService jwt;
  private final UserRepository users;
  public JwtAuthFilter(JwtService jwt, UserRepository users){ this.jwt=jwt; this.users=users; }

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest req, @NonNull HttpServletResponse res, @NonNull FilterChain chain)
      throws ServletException, IOException {
    String auth = req.getHeader("Authorization");
    if (auth != null && auth.startsWith("Bearer ")) {
      String token = auth.substring(7);
      try {
        String email = jwt.getSubject(token);
        users.findByEmail(email).ifPresent(u -> {
          var authToken = new UsernamePasswordAuthenticationToken(
              email, null, List.of(() -> "ROLE_"+u.getRole()));
          SecurityContextHolder.getContext().setAuthentication(authToken);
        });
      } catch (Exception ignored) {}
    }
    chain.doFilter(req, res);
  }
}