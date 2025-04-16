package io.pow.backend.product.entity;

import io.pow.backend.uom.UOM;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Embeddable
public class ProductUOMKey {
    @ManyToOne
    private Product product;
    @ManyToOne
    private UOM uom;
}