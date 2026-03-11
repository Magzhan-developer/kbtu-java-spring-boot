package kz.kbtu.lab5.mappers;

import kz.kbtu.lab5.dto.response.StudentDTO;
import kz.kbtu.lab5.dto.request.StudentRequestDTO;
import kz.kbtu.lab5.entities.StudentEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentEntity toEntity(StudentRequestDTO dto);

    StudentDTO toDTO(StudentEntity student);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateStudentFromDto(StudentRequestDTO dto, @MappingTarget StudentEntity student);
}
