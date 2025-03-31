package io.pow.backend.product;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductUOMRepository extends JpaRepository<ProductUOM, ProductUOMKey> {

   @Query("SELECT pu FROM ProductUOM pu WHERE pu.id.product.code = :productCode AND pu.id.uom.code = :uomCode")
   public Optional<ProductUOM> findByProductCodeAndUomCode(String productCode, String uomCode);
    
}
