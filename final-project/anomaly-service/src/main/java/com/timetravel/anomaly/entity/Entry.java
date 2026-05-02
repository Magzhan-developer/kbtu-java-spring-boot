package com.timetravel.anomaly.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "entries")
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "traveler_id", nullable = false)
    private Long travelerId;

    @Column(name = "destination_time", nullable = false)
    private Instant destinationTime;

    @Column(name = "location_name", nullable = false)
    private String locationName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt = Instant.now();
}
