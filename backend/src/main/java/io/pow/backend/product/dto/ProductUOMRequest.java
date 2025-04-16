package io.pow.backend.product.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;

@Data
public class ProductUOMRequest {
    private String code;
    private BigDecimal unitPrice;
    private UUID UOMID;
}
