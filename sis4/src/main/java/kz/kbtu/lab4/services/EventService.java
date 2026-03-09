package kz.kbtu.lab4.services;


import kz.kbtu.lab4.constants.Statuses;
import kz.kbtu.lab4.dto.CreateEventRequestDto;
import kz.kbtu.lab4.dto.CreateEventResponseDto;
import kz.kbtu.lab4.entities.EventEntity;
import kz.kbtu.lab4.mappers.EventMapper;
import kz.kbtu.lab4.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public List<EventEntity> getAllEvents(){
        return eventRepository.findAll();
    }

    public CreateEventResponseDto createEvent(CreateEventRequestDto body){

        EventEntity newEvent = eventMapper.toEntity(body);

        EventEntity savedEvent = eventRepository.save(newEvent);

        log.info("Created new event with id {}", savedEvent.getId());

        return CreateEventResponseDto.builder()
                .status(Statuses.SUCCESS.name())
                .details("Success event creation")
                .event(savedEvent)
                .build();
    }
}
