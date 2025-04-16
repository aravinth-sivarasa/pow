package io.pow.backend.product.entity;

import io.pow.backend.uom.UoM;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Embeddable
public class ProductUoMKey {
    @ManyToOne
    private Product product;
    @ManyToOne
    private UoM uom;
}