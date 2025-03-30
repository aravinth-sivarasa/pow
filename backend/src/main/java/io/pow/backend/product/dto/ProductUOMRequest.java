package io.pow.backend.product.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductUOMRequest {
    private String code;
    private BigDecimal unitPrice;
    private Long UOMID;
}
