package kz.kbtu.lab5.constants.swagger;

import io.swagger.v3.oas.annotations.media.Schema;
import kz.kbtu.lab5.dto.response.APIResponse;
import kz.kbtu.lab5.dto.response.StudentDTO;

@Schema(name = "StudentResponseWrapper", description = "API Response with StudentDTO")
public class StudentDTOWrapper extends APIResponse<StudentDTO> {
}
