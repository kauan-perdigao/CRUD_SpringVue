package com.myproject.spring_back.controller;

import com.myproject.spring_back.model.User;
import com.myproject.spring_back.repository.UserRepository;
import com.myproject.spring_back.security.JwtService;
import jakarta.validation.constraints.*;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController @RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:5173"})
public class AuthController {
  record RegisterRequest(@NotBlank String name, @Email String email, @NotBlank String password){}
  record LoginRequest(@Email String email, @NotBlank String password){}
  record TokenResponse(String token, String email, String name){}

  private final UserRepository users;
  private final PasswordEncoder encoder;
  private final JwtService jwt;

  public AuthController(UserRepository users, PasswordEncoder encoder, JwtService jwt){
    this.users = users; this.encoder = encoder; this.jwt = jwt;
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterRequest r){
    if (users.existsByEmail(r.email())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("E-mail já cadastrado");
    }
    User u = new User();
    u.setName(r.name()); u.setEmail(r.email());
    u.setPassword(encoder.encode(r.password()));
    users.save(u);
    String token = jwt.generate(u.getEmail(), Map.of("name", u.getName(), "role", u.getRole()));
    return ResponseEntity.ok(new TokenResponse(token, u.getEmail(), u.getName()));
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest r){
    var u = users.findByEmail(r.email()).orElse(null);
    if (u == null || !encoder.matches(r.password(), u.getPassword())) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
    }
    String token = jwt.generate(u.getEmail(), Map.of("name", u.getName(), "role", u.getRole()));
    return ResponseEntity.ok(new TokenResponse(token, u.getEmail(), u.getName()));
  }
}
