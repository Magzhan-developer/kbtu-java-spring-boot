package com.timetravel.anomaly.repository;

import com.timetravel.anomaly.entity.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {
    List<Entry> findByTravelerIdAndDestinationTime(Long travelerId, Instant destinationTime);
}
