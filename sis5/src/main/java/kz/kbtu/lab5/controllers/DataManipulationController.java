package kz.kbtu.lab5.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import kz.kbtu.lab5.services.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/data/generate")
@Hidden
@RequiredArgsConstructor
public class DataManipulationController {
    private final UtilService utilService;


    @PostMapping
    public ResponseEntity<String> generateData() {
        int count = utilService.generateStudents();

        return ResponseEntity.ok(count + " students generated successfully");
    }
}
