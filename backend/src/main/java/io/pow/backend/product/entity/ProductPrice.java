package io.pow.backend.product.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import io.pow.backend.currency.Currency;


@Data
@Entity
@Table(indexes = {
    @Index(name = "product_price_idx_product_token", columnList = "productToken"),
    @Index(name = "product_price_idx_currency_token", columnList = "currencyToken"),
    @Index(name = "product_price_idx_secrete_profile_id", columnList = "secreteProfileId")
})
public class ProductPrice {

    public ProductPrice() {
    }
    @EmbeddedId
    private ProductPriceKey id;
    private BigDecimal unitPrice;
    private UUID secreteProfileId;
    private Date createdOn;

    public ProductPrice(UUID productId, UUID uomId, BigDecimal unitPrice) {
        this.id = new ProductPriceKey();
        this.id.setProduct(new Product(productId));
        this.id.setCurrency(new Currency(uomId));
        this.unitPrice = unitPrice;
    }
}

