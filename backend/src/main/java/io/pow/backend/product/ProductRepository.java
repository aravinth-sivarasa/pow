package io.pow.backend.product;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.pow.backend.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    boolean existsBySku(String sku);
    Optional<Product> findBySku(String sku);
    
}
