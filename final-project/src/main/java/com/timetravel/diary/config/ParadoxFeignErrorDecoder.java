package com.timetravel.diary.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParadoxFeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();
    private static final Logger logger = LoggerFactory.getLogger(ParadoxFeignErrorDecoder.class);

    @Override
    public Exception decode(String methodKey, Response response) {
        logger.info("Feign client error intercepted: Status code - {}", response.status());

        if (response.status() == 404) {
            logger.error("404 Not Found from Paradox-Detector API. Method: {}", methodKey);
            return new RuntimeException("Paradox Detector Service Not Found (404)");
        } else if (response.status() == 500) {
            logger.error("500 Internal Server Error from Paradox-Detector API. Method: {}", methodKey);
            return new RuntimeException("Paradox Detector Service is experiencing issues (500 Error)");
        }

        // Delegate to standard error decoder for 400, 401, 403, 503, etc...
        return defaultErrorDecoder.decode(methodKey, response);
    }
}
