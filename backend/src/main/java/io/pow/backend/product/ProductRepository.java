package io.pow.backend.product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByCode(String code);
    // Custom query methods can be defined here if needed
    // For example, findByCode, findByDescription, etc.
    
}
