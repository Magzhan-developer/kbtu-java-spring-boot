package kz.kbtu.lab5.services;

import kz.kbtu.lab5.constants.enums.Status;
import kz.kbtu.lab5.dto.response.StudentDTO;
import kz.kbtu.lab5.dto.request.StudentRequestDTO;
import kz.kbtu.lab5.dto.response.APIResponse;
import kz.kbtu.lab5.entities.StudentEntity;
import kz.kbtu.lab5.exceptions.ResourceNotFoundException;
import kz.kbtu.lab5.mappers.StudentMapper;
import kz.kbtu.lab5.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository repository;
    private final StudentMapper mapper;

    public Page<StudentDTO> getAllStudents(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDTO);
    }

    public APIResponse<StudentDTO> getStudentById(Long id) {
        StudentEntity foundStudent = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        return new APIResponse<>(
                Status.SUCCESS,
                String.format("Student with id %d found", id),
                mapper.toDTO(foundStudent)
        );
    }

    public APIResponse<StudentDTO> createStudent(StudentRequestDTO dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Student with this email already exists");
        }

        StudentEntity student = mapper.toEntity(dto);
        StudentEntity savedStudent = repository.save(student);
        return new APIResponse<>(
                Status.SUCCESS,
                "Student created successfully",
                mapper.toDTO(savedStudent)
                );
    }

    public APIResponse<StudentDTO> updateStudent(Long id, StudentRequestDTO dto) {

        StudentEntity student = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        mapper.updateStudentFromDto(dto, student);

        StudentEntity updatedStudent = repository.save(student);

        return new APIResponse<>(
                Status.SUCCESS,
                String.format("Student with ID %d successfully updated", id),
                mapper.toDTO(updatedStudent)
        );
    }

    public APIResponse<StudentDTO> deleteStudent(Long id) {
        StudentEntity student = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        repository.delete(student);

        return new APIResponse<>(
                Status.SUCCESS,
                String.format("Student with ID %d successfully updated", id),
                null
        );
    }
}
