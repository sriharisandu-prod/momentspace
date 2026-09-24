package com.srihari.memorieshub.auth.service;

import com.srihari.memorieshub.auth.dto.AuthResponse;
import com.srihari.memorieshub.auth.dto.LoginRequest;
import com.srihari.memorieshub.auth.dto.RegisterRequest;
import com.srihari.memorieshub.role.entity.Role;
import com.srihari.memorieshub.role.repository.RoleRepository;
import com.srihari.memorieshub.security.jwt.JwtService;
import com.srihari.memorieshub.user.entity.User;
import com.srihari.memorieshub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import org.springframework.security.core.Authentication;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(RegisterRequest request) {

        if (userRepository
                .findByEmail(request.getEmail())
                .isPresent()) {

            throw new RuntimeException(
                    "Email already registered"
            );
        }

        Role role =
                roleRepository
                        .findByName("USER")
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "USER role not found"
                                )
                        );

        User user = new User();

        user.setUsername(
                request.getUsername()
        );

        user.setEmail(
                request.getEmail()
        );

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setRole(role);

        userRepository.save(user);
    }

    @Override
    public AuthResponse login(
            LoginRequest request
    ) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

        UserDetails userDetails =
                (UserDetails)
                        authentication.getPrincipal();

        String token =
                jwtService.generateToken(
                        userDetails
                );

        User user =
                userRepository
                        .findByEmail(
                                request.getEmail()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        return AuthResponse.builder()
                .id(user.getId())
                .token(token)
                .username(user.getUsername())
                .email(user.getEmail())
                .profilePhotoUrl(user.getProfilePhotoUrl())
                .build();
    }

}