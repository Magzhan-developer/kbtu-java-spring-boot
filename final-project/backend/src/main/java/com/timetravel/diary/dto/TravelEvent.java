package com.timetravel.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TravelEvent {
    private String eventId;
    private Long travelerId;
    private Instant destinationTime;
    private String locationName;
    private String description;
}
