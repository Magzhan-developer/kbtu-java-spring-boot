package com.timetravel.diary.service;

import com.timetravel.diary.config.JwtProvider;
import com.timetravel.diary.dto.AuthRequest;
import com.timetravel.diary.dto.AuthResponse;
import com.timetravel.diary.entity.RefreshToken;
import com.timetravel.diary.entity.User;
import com.timetravel.diary.repository.RefreshTokenRepository;
import com.timetravel.diary.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final LogService logService;

    @Transactional
    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        User user = userRepository.findByUsername(request.username())
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String role = user.getRoles().stream()
            .findFirst()
            .map(com.timetravel.diary.entity.Role::getName)
            .orElse("TRAVELER");

        String accessToken = jwtProvider.generateToken(user.getUsername(), role);
        String refreshToken = generateAndSaveRefreshToken(user);

        logService.save(user.getId(), "USER_LOGGED_IN", "User successfully logged in");
        return new AuthResponse(accessToken, refreshToken, user.getUsername(), "Login successful");
    }

    @Transactional
    public AuthResponse refresh(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
            .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));

        if (refreshToken.isRevoked() || refreshToken.getExpiresAt().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new IllegalArgumentException("Refresh token is expired or revoked");
        }

        User user = refreshToken.getUser();
        String role = user.getRoles().stream()
            .findFirst()
            .map(com.timetravel.diary.entity.Role::getName)
            .orElse("TRAVELER");

        String accessToken = jwtProvider.generateToken(user.getUsername(), role);
        return new AuthResponse(accessToken, refreshToken.getToken(), user.getUsername(), "Token refreshed successfully");
    }

    @Transactional
    public void logout(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
            
        refreshTokenRepository.deleteByUser(user);
        logService.save(user.getId(), "USER_LOGGED_OUT", "User successfully logged out");
    }

    private String generateAndSaveRefreshToken(User user) {
        refreshTokenRepository.deleteByUser(user);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiresAt(Instant.now().plus(7, ChronoUnit.DAYS));
        refreshToken.setRevoked(false);

        refreshTokenRepository.save(refreshToken);
        return refreshToken.getToken();
    }
}
