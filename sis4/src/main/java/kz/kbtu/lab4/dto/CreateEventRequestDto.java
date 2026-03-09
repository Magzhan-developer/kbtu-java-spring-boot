package kz.kbtu.lab4.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventRequestDto {

    @NotBlank(message = "Title cannot be empty")
    private String title;

    @Email
    @NotBlank(message = "Email cannot be empty")
    private String organizerEmail;

    @Min(value = 200)
    private Double ticketPrice;

    @NotBlank(message = "Description  cannot be empty")
    private String description;

    @NotNull(message = "Date cannot be null")
    @JsonProperty("eventStartingDate")
    private Instant date;

    @NotBlank(message = "Location cannot be empty")
    private String location;

    @NotBlank(message = "Category cannot be empty")
    private String category;
}
