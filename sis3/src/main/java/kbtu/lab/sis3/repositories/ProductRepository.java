package kbtu.lab.sis3.repositories;

import kbtu.lab.sis3.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
