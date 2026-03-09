package kz.kbtu.lab4.repositories;

import kz.kbtu.lab4.entities.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
}
