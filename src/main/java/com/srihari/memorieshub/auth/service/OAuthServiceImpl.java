package com.srihari.memorieshub.auth.service;

import com.srihari.memorieshub.auth.dto.AuthResponse;
import com.srihari.memorieshub.auth.dto.GoogleUserInfo;
import com.srihari.memorieshub.security.jwt.JwtService;
import com.srihari.memorieshub.security.userdetails.CustomUserDetails;
import com.srihari.memorieshub.user.entity.User;
import com.srihari.memorieshub.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuthServiceImpl implements OAuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RestTemplate restTemplate;

    @Value("${google.client-id}")
    private String clientId;

    @Value("${google.client-secret}")
    private String clientSecret;

    @Value("${google.redirect-uri}")
    private String redirectUri;

    @Override
    public AuthResponse processGoogleOAuth(String code) {

        // =========================================
        // 1. Exchange authorization code for token
        // =========================================

        String tokenUrl =
                "https://oauth2.googleapis.com/token";

        MultiValueMap<String, String> formData =
                new LinkedMultiValueMap<>();

        formData.add("code", code);
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("redirect_uri", redirectUri);
        formData.add("grant_type", "authorization_code");

        HttpHeaders tokenHeaders =
                new HttpHeaders();

        tokenHeaders.setContentType(
                MediaType.APPLICATION_FORM_URLENCODED
        );

        HttpEntity<MultiValueMap<String, String>> tokenRequest =
                new HttpEntity<>(
                        formData,
                        tokenHeaders
                );

        ResponseEntity<Map> tokenResponse =
                restTemplate.postForEntity(
                        tokenUrl,
                        tokenRequest,
                        Map.class
                );

        if (!tokenResponse.getStatusCode().is2xxSuccessful()
                || tokenResponse.getBody() == null) {

            throw new RuntimeException(
                    "Google token exchange failed"
            );
        }

        String accessToken =
                (String) tokenResponse
                        .getBody()
                        .get("access_token");

        // =========================================
        // 2. Get Google user information
        // =========================================

        String profileUrl =
                "https://www.googleapis.com/oauth2/v2/userinfo";

        HttpHeaders profileHeaders =
                new HttpHeaders();

        profileHeaders.setBearerAuth(accessToken);

        HttpEntity<Void> profileRequest =
                new HttpEntity<>(profileHeaders);

        ResponseEntity<GoogleUserInfo> profileResponse =
                restTemplate.exchange(
                        profileUrl,
                        HttpMethod.GET,
                        profileRequest,
                        GoogleUserInfo.class
                );

        GoogleUserInfo googleUser =
                profileResponse.getBody();

        if (googleUser == null
                || googleUser.getEmail() == null) {

            throw new RuntimeException(
                    "Unable to retrieve Google user"
            );
        }

        // =========================================
        // 3. Find or create user
        // =========================================

        User user =
                userRepository
                        .findByEmail(
                                googleUser.getEmail()
                        )
                        .orElseGet(() -> {

                            User newUser = new User();

                            newUser.setEmail(
                                    googleUser.getEmail()
                            );

                            newUser.setUsername(
                                    googleUser.getName()
                            );

                            newUser.setProfilePhotoUrl(
                                    googleUser.getPicture()
                            );

                            return userRepository.save(
                                    newUser
                            );
                        });

        // =========================================
        // 4. Generate MemoriesHub JWT
        // =========================================

        String jwt =
                jwtService.generateToken(
                        new CustomUserDetails(user)
                );

        // =========================================
        // 5. Return authentication response
        // =========================================

        return AuthResponse.builder()
                .token(jwt)
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}

