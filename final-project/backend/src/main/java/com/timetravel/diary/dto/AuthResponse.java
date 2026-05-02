package com.timetravel.diary.dto;

public record AuthResponse(
    String accessToken,
    String refreshToken,
    String username,
    String message
) {}
