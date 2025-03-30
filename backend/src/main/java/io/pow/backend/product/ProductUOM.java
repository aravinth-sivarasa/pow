package io.pow.backend.product;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

import io.pow.backend.uom.UOM;

@Data
@Entity
public class ProductUOM {

    @EmbeddedId
    private ProductUOMKey id;
    private BigDecimal unitPrice;
    public ProductUOM(Long productId, Long uomId, BigDecimal unitPrice) {
        this.id = new ProductUOMKey();
        this.id.setProduct(new Product(productId));
        this.id.setUom(new UOM(uomId));
        this.unitPrice = unitPrice;
    }
}

@Data
@Embeddable
class ProductUOMKey {
    @ManyToOne
    private Product product;
    @ManyToOne
    private UOM uom;
}