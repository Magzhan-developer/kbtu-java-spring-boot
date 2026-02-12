package kbtu.lab.sis2.controllers;

import kbtu.lab.sis2.dto.ExternalApiGettingDataResponseDto;
import kbtu.lab.sis2.services.ExternalApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*@RequiredArgsConstructor*/
@RestController
@RequestMapping("/api")
public class BaseController {

    private final ExternalApiService service;

    public BaseController(ExternalApiService service) {
        this.service = service;
    }

    @GetMapping("/data")
    public ResponseEntity<ExternalApiGettingDataResponseDto> getData(@RequestParam String name){
        ExternalApiGettingDataResponseDto data = service.getData(name);
        return ResponseEntity.ok(data);
    }

}
