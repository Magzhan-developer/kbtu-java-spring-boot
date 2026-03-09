package kz.kbtu.lab4.dto;

import jakarta.validation.constraints.NotNull;
import kz.kbtu.lab4.entities.EventEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateEventResponseDto {
    @NotNull
    private String details;

    @NotNull
    private String status;

    private EventEntity event;
}
