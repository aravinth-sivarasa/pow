package io.pow.backend.product;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.pow.backend.product.entity.ProductUoM;
import io.pow.backend.product.entity.ProductUoMKey;

public interface ProductUOMRepository extends JpaRepository<ProductUoM, ProductUoMKey> {

   @Query("SELECT pu FROM ProductUoM pu WHERE pu.id.product.code = :productCode AND pu.id.uom.code = :uomCode")
   public Optional<ProductUoM> findByProductCodeAndUomCode(String productCode, String uomCode);
    
}
