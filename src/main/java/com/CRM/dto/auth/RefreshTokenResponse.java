package com.CRM.dto.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshTokenResponse {

    private String accessToken;

    private String refreshToken;

    private String tokenType;

    private Long expiresIn;
}