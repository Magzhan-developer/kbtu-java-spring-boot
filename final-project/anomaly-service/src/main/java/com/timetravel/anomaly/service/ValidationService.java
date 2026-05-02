package com.timetravel.anomaly.service;

import com.timetravel.anomaly.dto.TravelEvent;
import com.timetravel.anomaly.entity.Entry;
import com.timetravel.anomaly.entity.Log;
import com.timetravel.anomaly.repository.EntryRepository;
import com.timetravel.anomaly.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ValidationService {

    private final EntryRepository entryRepository;
    private final LogRepository logRepository;

    @Transactional
    public void validateAndSave(TravelEvent event) {
        log.info("Validating event: {}", event.getEventId());
        List<Entry> existingEntries = entryRepository.findByTravelerIdAndDestinationTime(event.getTravelerId(), event.getDestinationTime());

        if (!existingEntries.isEmpty()) {
            boolean isDuplicate = existingEntries.stream().anyMatch(e ->
                    e.getLocationName().equals(event.getLocationName()) &&
                    (e.getDescription() != null && e.getDescription().equals(event.getDescription()))
            );

            if (isDuplicate) {
                log.warn("Duplicate entry blocked for traveler: {}", event.getTravelerId());
                saveLog(event.getTravelerId(), "DUPLICATE_BLOCKED", "Attempted to create duplicate entry for " + event.getDestinationTime());
            } else {
                log.warn("Paradox detected for traveler: {}", event.getTravelerId());
                saveLog(event.getTravelerId(), "PARADOX_DETECTED", "Traveler cannot be at two different places at the exact same moment: " + event.getDestinationTime());
            }
            return;
        }

        Entry newEntry = new Entry();
        newEntry.setTravelerId(event.getTravelerId());
        newEntry.setDestinationTime(event.getDestinationTime());
        newEntry.setLocationName(event.getLocationName());
        newEntry.setDescription(event.getDescription());

        entryRepository.save(newEntry);
        log.info("Entry saved successfully for traveler: {}", event.getTravelerId());

        saveLog(event.getTravelerId(), "ENTRY_CREATED", "New time travel entry created for " + event.getDestinationTime());
    }

    private void saveLog(Long userId, String action, String details) {
        Log actionLog = new Log();
        actionLog.setUserId(userId);
        actionLog.setAction(action);
        actionLog.setDetails(details);
        logRepository.save(actionLog);
    }
}
