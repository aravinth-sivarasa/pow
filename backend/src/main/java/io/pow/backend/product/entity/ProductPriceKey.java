package io.pow.backend.product.entity;




import io.pow.backend.currency.Currency;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Embeddable
public class ProductPriceKey {
    @ManyToOne
    private Product product;
    @ManyToOne
    private Currency currency;
}