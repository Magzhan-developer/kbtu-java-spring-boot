package kz.kbtu.lab5.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import kz.kbtu.lab5.constants.enums.Status;
import kz.kbtu.lab5.constants.swagger.StudentDTOWrapper;
import kz.kbtu.lab5.dto.response.StudentDTO;
import kz.kbtu.lab5.dto.request.StudentRequestDTO;
import kz.kbtu.lab5.dto.response.APIResponse;
import kz.kbtu.lab5.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
    private final StudentService service;

    @Operation(summary = "Get all Students With Pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All Students Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = StudentDTOWrapper.class)),
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @GetMapping
    public ResponseEntity<APIResponse<Page<StudentDTO>>> getAllStudents(@ParameterObject Pageable pageable) {
        Page<StudentDTO> students = service.getAllStudents(pageable);

        return ResponseEntity.ok(
                new APIResponse<>(
                        Status.SUCCESS,
                        "Students fetched successfully",
                        students
                )
        );

    }

    @Operation(summary = "Get Specific Student By ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Student With Such ID Not Found", content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<StudentDTO>> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getStudentById(id));
    }

    @Operation(summary = "Create A New Student Profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New Student Profile Successfully created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Student With Such Email Already Exists", content = @Content),
            @ApiResponse(responseCode = "404", description = "Student With Such ID Not Found", content = @Content)
    })
    @PostMapping
    public ResponseEntity<APIResponse<StudentDTO>> createStudent(@RequestBody @Valid StudentRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createStudent(dto));
    }


    @Operation(summary = "Update Student Profile partially or Fully")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student Profile Successfully Updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Student With Such ID Not Found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<StudentDTO>> updateStudent(@PathVariable Long id, @RequestBody StudentRequestDTO dto) {
        return ResponseEntity.ok(service.updateStudent(id, dto));
    }

    @Operation(summary = "Deleting Specific Student By ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student Successfully Deleted", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Student With Such ID Not Found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<StudentDTO>> deleteStudent(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteStudent(id));
    }

}
