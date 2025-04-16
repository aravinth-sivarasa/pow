package io.pow.backend.product.entity;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(indexes = {
    @Index(name = "product_idx_code", columnList = "code"),
    @Index(name = "product_idx_token", columnList = "token"),
    @Index(name = "product_idx_secrete_profile_id", columnList = "secreteProfileId")
})
public class Product {
    
    public Product() {
        super();
    }
    public Product(UUID productId) {
        this.token = productId;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID token;
    @Column(unique = true, nullable = false)
    private String code;
    private String name;
    private String description;
    private UUID secreteProfileId;
    private Date createdOn;
}
