package com.timetravel.diary.service;

import com.timetravel.diary.dto.EntryCreateRequest;
import com.timetravel.diary.dto.EntryResponse;
import com.timetravel.diary.dto.EntryUpdateRequest;
import com.timetravel.diary.dto.TravelEvent;
import com.timetravel.diary.entity.Entry;
import com.timetravel.diary.entity.User;
import com.timetravel.diary.repository.EntryRepository;
import com.timetravel.diary.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EntryService {

    private final EntryRepository entryRepository;
    private final UserRepository userRepository;
    private final TravelEventProducer travelEventProducer;
    private final LogService logService;

    public List<EntryResponse> getAllEntries() {
        return entryRepository.findAll().stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    public List<EntryResponse> getEntriesForCurrentUser() {
        User currentUser = getCurrentUser();
        return entryRepository.findByTravelerId(currentUser.getId()).stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    public EntryResponse getEntryById(Long id) {
        Entry entry = entryRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Entry not found"));
        return mapToResponse(entry);
    }

    @Transactional
    public void createEntryAsync(EntryCreateRequest request) {
        User currentUser = getCurrentUser();
        
        TravelEvent event = new TravelEvent(
            UUID.randomUUID().toString(),
            currentUser.getId(),
            request.destinationTime(),
            request.locationName(),
            request.description()
        );
        
        travelEventProducer.publishEvent(event);
        logService.save(currentUser.getId(), "CREATE_ENTRY", "Entry creation requested and sent to Kafka");
        log.info("Published TravelEvent for validation: user {}", currentUser.getUsername());
    }

    @Transactional
    public EntryResponse updateEntry(Long id, EntryUpdateRequest request) {
        Entry entry = entryRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Entry not found"));

        entry.setDestinationTime(request.destinationTime());
        entry.setLocationName(request.locationName());
        entry.setDescription(request.description());

        entryRepository.save(entry);

        log.info("Entry {} updated", id);
        logService.save(entry.getTraveler().getId(), "ENTRY_UPDATED", "Entry updated by traveler");
        return mapToResponse(entry);
    }

    @Transactional
    public void deleteEntry(Long id) {
        Entry entry = entryRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Entry not found"));

        Long travelerId = entry.getTraveler().getId();
        entryRepository.delete(entry);
        log.info("Entry {} deleted", id);
        logService.save(travelerId, "ENTRY_DELETED", "Entry deleted by traveler");
    }

    public boolean isOwnerOrAdmin(Long entryId) {
        Entry entry = entryRepository.findById(entryId)
            .orElseThrow(() -> new IllegalArgumentException("Entry not found"));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return true;
        }

        User currentUser = getCurrentUser();
        return entry.getTraveler().getId().equals(currentUser.getId());
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalStateException("Current user not found"));
    }

    private EntryResponse mapToResponse(Entry entry) {
        return new EntryResponse(
            entry.getId(),
            entry.getTraveler().getId(),
            entry.getDestinationTime(),
            entry.getLocationName(),
            entry.getDescription(),
            entry.getCreatedAt()
        );
    }
}
