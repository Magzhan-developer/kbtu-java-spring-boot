package com.timetravel.diary.service;

import com.timetravel.diary.entity.Log;
import com.timetravel.diary.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogService {

    private final LogRepository logRepository;

    public void save(Long userId, String action, String details) {
        try {
            Log actionLog = new Log();
            actionLog.setUserId(userId);
            actionLog.setAction(action);
            actionLog.setDetails(details);
            logRepository.save(actionLog);
            log.info("Action logged: {} for user {}", action, userId);
        } catch (Exception e) {
            log.error("Failed to save log: {}", e.getMessage(), e);
        }
    }

    public List<Log> getAllLogs() {
        return logRepository.findAll();
    }

    public Log getLogById(Long id) {
        return logRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Log not found"));
    }

    public void deleteLog(Long id) {
        logRepository.deleteById(id);
    }
}
