package com.CRM.service.Impl;

import com.CRM.entity.RefreshToken;
import com.CRM.entity.User;
import com.CRM.exception.ResourceNotFoundException;
import com.CRM.repository.RefreshTokenRepository;
import com.CRM.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private static final long REFRESH_TOKEN_VALIDITY_DAYS = 7;

//    @Override
//    public RefreshToken createRefreshToken(User user) {
//
//        refreshTokenRepository.deleteByUser(user);
//
//        RefreshToken refreshToken = RefreshToken.builder()
//                .user(user)
//                .token(UUID.randomUUID().toString())
//                .expiryDate(LocalDateTime.now().plusDays(REFRESH_TOKEN_VALIDITY_DAYS))
//                .revoked(false)
//                .active(true)
//                .deleted(false)
//                .build();
//
//        return refreshTokenRepository.save(refreshToken);
//    }

    @Override
    public RefreshToken createRefreshToken(User user) {

        RefreshToken refreshToken =
                refreshTokenRepository.findByUser(user)
                        .orElse(new RefreshToken());

        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(
                LocalDateTime.now().plusDays(REFRESH_TOKEN_VALIDITY_DAYS));
        refreshToken.setRevoked(false);
        refreshToken.setActive(true);
        refreshToken.setDeleted(false);

        return refreshTokenRepository.save(refreshToken);
    }
    @Override
    public RefreshToken verifyExpiration(String token) {

        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Refresh token not found."));

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {

            refreshTokenRepository.delete(refreshToken);

            throw new ResourceNotFoundException("Refresh token expired.");
        }

        return refreshToken;
    }

    @Override
    public void deleteByUser(User user) {

        refreshTokenRepository.deleteByUser(user);
    }
}