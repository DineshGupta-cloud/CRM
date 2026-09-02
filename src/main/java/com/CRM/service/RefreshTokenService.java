package com.CRM.service;

import com.CRM.entity.RefreshToken;
import com.CRM.entity.User;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(User user);

    RefreshToken verifyExpiration(String token);

    void deleteByUser(User user);
}