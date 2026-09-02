package com.CRM.controller;

import com.CRM.dto.auth.*;
import com.CRM.dto.response.ApiResponse;
import com.CRM.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        System.out.println("request " + request);
        LoginResponse response = authService.login(request);

        System.out.println("Response " + response.getUsername());
        System.out.println("Response " + response.getUserId());
        System.out.println("Response " + response.getEmployeeId());
        System.out.println("Response " + response.getFullName());

        return ResponseEntity.ok(
                ApiResponse.<LoginResponse>builder()
                        .success(true)
                        .message("Login successful.")
                        .data(response)
                        .build()
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(
            @Valid @RequestBody RegisterUserRequest request) {

        authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<String>builder()
                                .success(true)
                                .message("User registered successfully.")
                                .data("Registration completed.")
                                .build()
                );
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<RefreshTokenResponse>> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request) {

        return ResponseEntity.ok(

                ApiResponse.<RefreshTokenResponse>builder()
                        .success(true)
                        .message("Token refreshed successfully.")
                        .data(authService.refreshToken(request))
                        .build()
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(
            @Valid @RequestBody RefreshTokenRequest request) {

        authService.logout(request);

        return ResponseEntity.ok(

                ApiResponse.<String>builder()
                        .success(true)
                        .message("Logout successful.")
                        .data("User logged out.")
                        .build()
        );
    }
}