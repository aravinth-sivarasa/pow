package io.pow.backend.product.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

import lombok.Data;
;

@Data
@Entity
public class Product {
    
    public Product() {
        super();
    }
    public Product(Long productId) {
        this.id = productId;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_sequence", allocationSize = 1)
    private Long id;
    private String code;
    private String description;

}
