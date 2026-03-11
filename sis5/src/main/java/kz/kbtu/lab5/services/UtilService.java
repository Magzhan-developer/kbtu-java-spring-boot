package kz.kbtu.lab5.services;

import com.github.javafaker.Faker;
import kz.kbtu.lab5.constants.enums.Gender;
import kz.kbtu.lab5.entities.StudentEntity;
import kz.kbtu.lab5.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UtilService {
    private final Faker faker = new Faker();
    private final StudentRepository repository;

    public int generateStudents() throws RuntimeException {

        List<StudentEntity> students = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            StudentEntity student = new StudentEntity();
            student.setName(faker.name().fullName());
            student.setEmail(faker.internet().emailAddress());
            student.setAge(faker.number().numberBetween(18, 25));

            Gender gender = faker.bool().bool() ? Gender.MALE : Gender.FEMALE;
            student.setGender(gender);

            students.add(student);
        }

        repository.saveAll(students);
        return students.size();
    }
}
