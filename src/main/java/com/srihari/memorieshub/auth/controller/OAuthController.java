package com.srihari.memorieshub.auth.controller;

import com.srihari.memorieshub.auth.dto.AuthResponse;
import com.srihari.memorieshub.auth.service.OAuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthService oauthService;

    @GetMapping("/callback/google")
    public AuthResponse handleGoogleCallback(
            @RequestParam("code") String code
    ) {

        return oauthService.processGoogleOAuth(code);
    }
}

