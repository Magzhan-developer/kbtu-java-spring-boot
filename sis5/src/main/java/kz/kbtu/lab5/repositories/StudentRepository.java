package kz.kbtu.lab5.repositories;

import kz.kbtu.lab5.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    Optional<StudentEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}
