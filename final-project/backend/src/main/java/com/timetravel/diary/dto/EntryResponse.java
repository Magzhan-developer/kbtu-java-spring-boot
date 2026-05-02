package com.timetravel.diary.dto;

import java.time.Instant;

public record EntryResponse(
    Long id,
    Long travelerId,
    Instant destinationTime,
    String locationName,
    String description,
    Instant createdAt
) {}
