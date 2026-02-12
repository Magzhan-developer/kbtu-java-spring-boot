package kbtu.lab.sis2.services;

import kbtu.lab.sis2.dto.ExternalApiGettingDataResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ExternalApiService {
    private final RestTemplate restTemplate;

    public ExternalApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ExternalApiGettingDataResponseDto getData(String name){
        String url = UriComponentsBuilder
                        .fromUriString("https://api.agify.io/")
                        .queryParam("name", name)
                        .build()
                        .toUriString();

        return restTemplate.getForObject(url, ExternalApiGettingDataResponseDto.class);
    }

}
