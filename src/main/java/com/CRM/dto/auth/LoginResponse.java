package com.CRM.dto.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    private String accessToken;

    private String refreshToken;

    private String tokenType;

    private Long userId;

    private String username;

    private String role;

    private String fullName;

    private Long employeeId;

    private Long expiresIn;
}