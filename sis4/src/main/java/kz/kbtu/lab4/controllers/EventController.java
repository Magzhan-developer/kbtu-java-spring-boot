package kz.kbtu.lab4.controllers;

import jakarta.validation.Valid;
import kz.kbtu.lab4.dto.CreateEventRequestDto;
import kz.kbtu.lab4.dto.CreateEventResponseDto;
import kz.kbtu.lab4.entities.EventEntity;
import kz.kbtu.lab4.services.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/event")
@Slf4j
public class EventController {
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventEntity>> getAllEvents(){
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @PostMapping
    public ResponseEntity<CreateEventResponseDto> createEvent (@RequestBody @Valid CreateEventRequestDto body){

        log.info("Creating new event with title {}", body.getTitle());

        CreateEventResponseDto response = eventService.createEvent(body);

        return ResponseEntity.ok(response);
    }
}

