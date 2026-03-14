package kbtu.lab6.feed_service.services;

import kbtu.lab6.feed_service.dto.PostCreatedEventDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PostConsumer {

    @KafkaListener(topics = "posts", groupId = "feed-group")
    public void consume(PostCreatedEventDTO dto) {
        System.out.println("Result of the KafkaListener: " + dto);
    }
}
