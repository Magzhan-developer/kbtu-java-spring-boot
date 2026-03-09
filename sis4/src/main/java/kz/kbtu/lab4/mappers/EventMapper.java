package kz.kbtu.lab4.mappers;

import kz.kbtu.lab4.dto.CreateEventRequestDto;
import kz.kbtu.lab4.entities.EventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(source = "date", target = "eventDate")
    EventEntity toEntity(CreateEventRequestDto dto);
}
