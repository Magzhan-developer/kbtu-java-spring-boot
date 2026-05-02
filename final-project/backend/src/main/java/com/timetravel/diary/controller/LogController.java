package com.timetravel.diary.controller;

import com.timetravel.diary.entity.Log;
import com.timetravel.diary.service.LogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logs")
@Tag(name = "Logs", description = "Audit logs management")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all logs", description = "Returns a list of all audit logs.")
    public ResponseEntity<List<Log>> getAllLogs() {
        return ResponseEntity.ok(logService.getAllLogs());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get log by ID", description = "Returns a single log by its ID.")
    public ResponseEntity<Log> getLogById(@PathVariable Long id) {
        return ResponseEntity.ok(logService.getLogById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete log", description = "Deletes a log by its ID.")
    public ResponseEntity<Void> deleteLog(@PathVariable Long id) {
        logService.deleteLog(id);
        return ResponseEntity.noContent().build();
    }
}
