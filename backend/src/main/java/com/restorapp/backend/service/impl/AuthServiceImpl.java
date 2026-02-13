package com.restorapp.backend.service.impl;

import com.restorapp.backend.dto.AuthResponse;
import com.restorapp.backend.dto.LoginRequest;
import com.restorapp.backend.dto.RegisterRequest;
import com.restorapp.backend.entity.Role;
import com.restorapp.backend.entity.User;
import com.restorapp.backend.repository.UserRepository;
import com.restorapp.backend.security.JwtTokenProvider;
import com.restorapp.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public AuthResponse login(LoginRequest request) {
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        request.getEmail(), request.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String token = jwtTokenProvider.generateToken(authentication);
    User user = userRepository.findByEmail(request.getEmail()).orElseThrow();

    return AuthResponse.builder()
        .token(token)
        .role(user.getRole().name())
        .build();
  }

  @Override
  public AuthResponse register(RegisterRequest request) {
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new RuntimeException("El email ya está registrado");
    }

    User user = User.builder()
        .name(request.getName())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(Role.USER)
        .build();

    userRepository.save(user);

    return login(LoginRequest.builder()
        .email(request.getEmail())
        .password(request.getPassword())
        .build());
  }
}
