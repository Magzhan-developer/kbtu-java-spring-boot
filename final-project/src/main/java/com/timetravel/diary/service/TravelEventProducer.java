package com.timetravel.diary.service;

import com.timetravel.diary.dto.TravelEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TravelEventProducer {

    private static final String TOPIC = "timeline-events";
    
    // Injecting KafkaTemplate to send events to topic
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public TravelEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Publishes a TravelEvent whenever a new diary entry is created.
     */
    public void publishEvent(TravelEvent event) {
        // We use eventId as the Kafka message key to ensure ordered delivery per event
        kafkaTemplate.send(TOPIC, event.getEventId(), event);
        System.out.println("Published TravelEvent to '" + TOPIC + "': " + event.getEventId());
    }
}
