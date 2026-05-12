package com.example.library.service.interfaces;

import com.example.library.dto.request.LoginRequest;
import com.example.library.dto.request.RegisterRequest;
import com.example.library.dto.response.JwtResponse;

public interface AuthService {
    JwtResponse login(LoginRequest loginRequest);
    String register(RegisterRequest registerRequest);
}
