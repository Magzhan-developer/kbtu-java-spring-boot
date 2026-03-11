package kz.kbtu.lab5.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Basis Error Response Body")
public class BasicErrorResponse {

    @Schema(example = "unallowed email pattern was send")
    private String message;
}
