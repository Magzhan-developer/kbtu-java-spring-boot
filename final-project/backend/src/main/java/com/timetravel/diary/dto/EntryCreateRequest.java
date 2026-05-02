package com.timetravel.diary.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;

public record EntryCreateRequest(
    @NotNull(message = "Destination time is required")
    Instant destinationTime,
    @NotBlank(message = "Location name is required")
    String locationName,
    String description
) {}
