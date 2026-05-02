package com.timetravel.diary.controller;

import com.timetravel.diary.dto.EntryCreateRequest;
import com.timetravel.diary.dto.EntryResponse;
import com.timetravel.diary.dto.EntryUpdateRequest;
import com.timetravel.diary.service.EntryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entries")
@Tag(name = "Diary Entries", description = "Operations for managing time travel diary entries")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@Slf4j
public class EntryController {

    private final EntryService entryService;

    @PostMapping
    @PreAuthorize("hasRole('TRAVELER')")
    @Operation(summary = "Create a time log", description = "Submit a new diary entry for timeline validation.")
    public ResponseEntity<String> createEntry(@Valid @RequestBody EntryCreateRequest request) {
        entryService.createEntryAsync(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Entry submitted for timeline validation");
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAVELER')")
    @Operation(summary = "List diary entries", description = "Returns all entries for admin and the current traveler's own entries.")
    public ResponseEntity<List<EntryResponse>> getEntries() {
        List<EntryResponse> entries;
        System.out.println("hasAdminRole(): " + hasAdminRole());
        if (hasAdminRole()) {
            entries = entryService.getAllEntries();
        } else {
            entries = entryService.getEntriesForCurrentUser();
        }
        return ResponseEntity.ok(entries);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('TRAVELER') and @entryService.isOwnerOrAdmin(#id))")
    @Operation(summary = "Get a diary entry", description = "Retrieve a single entry by ID, with ownership checks.", responses = {
        @ApiResponse(responseCode = "200", description = "Entry found"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<EntryResponse> getEntryById(@PathVariable Long id) {
        EntryResponse entry = entryService.getEntryById(id);
        return ResponseEntity.ok(entry);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('TRAVELER') and @entryService.isOwnerOrAdmin(#id))")
    @Operation(summary = "Update a diary entry", description = "Update a diary entry if the authenticated user is the owner or an admin.")
    public ResponseEntity<EntryResponse> updateEntry(@PathVariable Long id, @Valid @RequestBody EntryUpdateRequest request) {
        EntryResponse updatedEntry = entryService.updateEntry(id, request);
        return ResponseEntity.ok(updatedEntry);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('TRAVELER') and @entryService.isOwnerOrAdmin(#id))")
    @Operation(summary = "Delete a diary entry", description = "Delete a diary entry if the authenticated user is the owner or an admin.")
    public ResponseEntity<Void> deleteEntry(@PathVariable Long id) {
        entryService.deleteEntry(id);
        return ResponseEntity.noContent().build();
    }

    private boolean hasAdminRole() {
        return SecurityContextHolder.getContext()
            .getAuthentication()
            .getAuthorities()
            .stream()
            .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
    }
}
