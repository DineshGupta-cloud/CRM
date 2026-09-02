package com.CRM.service;

import com.CRM.dto.auth.*;

public interface AuthService {

    LoginResponse login(LoginRequest request);

    void register(RegisterUserRequest request);

    RefreshTokenResponse refreshToken(RefreshTokenRequest request);

    void logout(RefreshTokenRequest request);

}