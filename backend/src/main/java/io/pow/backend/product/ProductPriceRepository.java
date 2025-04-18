package io.pow.backend.product;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.pow.backend.product.entity.ProductPrice;
import io.pow.backend.product.entity.ProductPriceKey;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, ProductPriceKey> {

   @Query("SELECT pp FROM ProductPrice pp WHERE pp.id.product.sku = :productCode AND pp.id.currency.code = :currencyCode")
   public Optional<ProductPrice> findByProductPriceAndCurrencyCode(String productSku, String currencyCode);
    
}
