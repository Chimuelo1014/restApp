package com.restorapp.backend.service;

import com.restorapp.backend.dto.LoginRequest;
import com.restorapp.backend.dto.RegisterRequest;
import com.restorapp.backend.dto.AuthResponse;

public interface AuthService {
  AuthResponse login(LoginRequest request);

  AuthResponse register(RegisterRequest request);
}
