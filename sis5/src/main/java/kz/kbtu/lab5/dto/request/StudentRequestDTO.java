package kz.kbtu.lab5.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import kz.kbtu.lab5.constants.enums.Gender;
import lombok.*;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request Body For Creating New Student")
public class StudentRequestDTO {

    @NotBlank(message = "Name is required")
    @Schema(example = "Almas Maratov")
    private String name;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    @Schema(example = "almasmaratov@gmail.com")
    private String email;

    @Min(value = 16, message = "Student must be at least 16 years old")
    @Max(value = 120)
    @Schema(example = "22")
    private Integer age;

    @NotNull
    @Schema(example = "MALE")
    private Gender gender;
}
