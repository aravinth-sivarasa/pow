package io.pow.backend.product;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.pow.backend.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByCode(String code);
    Optional<Product> findByCode(String code);
    
}
