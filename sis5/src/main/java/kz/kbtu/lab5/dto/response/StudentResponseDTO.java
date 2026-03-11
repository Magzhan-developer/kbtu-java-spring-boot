package kz.kbtu.lab5.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kz.kbtu.lab5.constants.enums.Status;
import kz.kbtu.lab5.entities.StudentEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Basic Response Body")
public class StudentResponseDTO {
    @Schema(example = "SUCCESS")
    private Status status;

    @Schema(example = "Student successfully created")
    private String details;

    @Schema(example = "{\n" +
            "  \"name\": \"Dana Akhmet\",\n" +
            "  \"email\": \"dana.akhmet@example.com\",\n" +
            "  \"age\": 19,\n" +
            "  \"gender\": \"FEMALE\"\n" +
            "}")
    private StudentDTO student;

    public StudentResponseDTO(Status status, String message) {
    }
}
