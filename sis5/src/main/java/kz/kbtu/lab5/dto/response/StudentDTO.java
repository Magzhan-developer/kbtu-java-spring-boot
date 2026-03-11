package kz.kbtu.lab5.dto.response;

import kz.kbtu.lab5.constants.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentDTO {
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private Gender gender;
}
