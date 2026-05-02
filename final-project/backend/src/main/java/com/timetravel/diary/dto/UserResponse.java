package com.timetravel.diary.dto;

import java.time.Instant;

public record UserResponse(
    Long id,
    String username,
    String email
) {}
