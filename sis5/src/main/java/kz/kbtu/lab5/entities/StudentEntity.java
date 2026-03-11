package kz.kbtu.lab5.entities;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import kz.kbtu.lab5.constants.enums.Gender;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "students")
@Schema(description = "Entity Object For Student")
@Hidden
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(example = "Almas Maratov")
    private String name;

    @Column(nullable = false, unique = true)
    @Schema(example = "almasmaratov@gmail.com")
    private String email;

    @Column(nullable = false)
    @Schema(example = "22")
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(example = "MALE")
    private Gender gender;
}
