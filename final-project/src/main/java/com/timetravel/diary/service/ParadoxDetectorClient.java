package com.timetravel.diary.service;

import com.timetravel.diary.config.ParadoxFeignErrorDecoder;
import com.timetravel.diary.dto.TravelEvent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Feign client simulating a connection to an external Paradox-Detector microservice.
 * We load the specific ErrorDecoder through the configuration scope below.
 */
@FeignClient(
    name = "paradox-detector", 
    url = "${paradox.service.url:http://localhost:8081}",
    configuration = ParadoxDetectorClient.FeignConfig.class
)
public interface ParadoxDetectorClient {

    @PostMapping("/api/paradox/check")
    boolean checkTimelineForParadox(@RequestBody TravelEvent event);

    /**
     * Isolated configuration ensures that this ErrorDecoder is only 
     * applied to this specific Feign Client and not globally.
     */
    class FeignConfig {
        @Bean
        public ParadoxFeignErrorDecoder paradoxFeignErrorDecoder() {
            return new ParadoxFeignErrorDecoder();
        }
    }
}
