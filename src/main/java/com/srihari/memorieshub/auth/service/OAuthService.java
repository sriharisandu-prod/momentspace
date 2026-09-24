package com.srihari.memorieshub.auth.service;

import com.srihari.memorieshub.auth.dto.AuthResponse;

public interface OAuthService {
    AuthResponse processGoogleOAuth(String code);
}
