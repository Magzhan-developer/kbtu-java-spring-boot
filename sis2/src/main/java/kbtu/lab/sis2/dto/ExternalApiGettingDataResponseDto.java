package kbtu.lab.sis2.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExternalApiGettingDataResponseDto {
    @NotNull
    private Long count;

    @NotNull
    private String name;

    @NotNull
    private Long age;
}
