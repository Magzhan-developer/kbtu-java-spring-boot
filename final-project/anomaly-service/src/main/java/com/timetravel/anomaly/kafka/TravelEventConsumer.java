package com.timetravel.anomaly.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.timetravel.anomaly.dto.TravelEvent;
import com.timetravel.anomaly.service.ValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TravelEventConsumer {

    private final ValidationService validationService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "timeline-events", groupId = "anomaly-group")
    public void consumeEvent(String message) {
        log.info("Raw Kafka message: {}", message);
        try {
            JsonNode jsonNode = objectMapper.readTree(message);
            if (jsonNode.isTextual()) {
                message = jsonNode.asText();
            }
            TravelEvent event = objectMapper.readValue(message, TravelEvent.class);
            validationService.validateAndSave(event);
        } catch (Exception e) {
            log.error("Failed to parse Kafka message", e);
        }
    }
}
