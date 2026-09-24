package com.srihari.memorieshub.auth.service;



import com.srihari.memorieshub.auth.dto.AuthResponse;
import com.srihari.memorieshub.auth.dto.LoginRequest;
import com.srihari.memorieshub.auth.dto.RegisterRequest;

public interface AuthService {

    AuthResponse login(
            LoginRequest request
    );

    void register(
            RegisterRequest request
    );
}