package io.pow.backend.product.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import io.pow.backend.uom.UoM;

@Data
@Entity
@Table(indexes = {
    @Index(name = "product_uom_idx_product_token", columnList = "product_token"),
    @Index(name = "product_uom_idx_uom_token", columnList = "uom_token"),
    @Index(name = "product_uom_idx_secrete_profile_id", columnList = "secreteProfileId")
})
public class ProductUoM {

    public ProductUoM() {
    }
    @EmbeddedId
    private ProductUoMKey id;
    private BigDecimal unitPrice;
    private UUID secreteProfileId;
    private Date createdOn;

    public ProductUoM(UUID productId, UUID uomId, BigDecimal unitPrice) {
        this.id = new ProductUoMKey();
        this.id.setProduct(new Product(productId));
        this.id.setUom(new UoM(uomId));
        this.unitPrice = unitPrice;
    }
}

