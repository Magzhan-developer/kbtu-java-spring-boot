package kbtu.lab.sis2.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetDataRequestDto {
    @NotNull(message = "name must be provided")
    private String name;
}
