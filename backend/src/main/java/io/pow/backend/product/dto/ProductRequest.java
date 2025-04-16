package io.pow.backend.product.dto;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class ProductRequest {
    private String code;
    private String description;
    private UUID productId;
    private List<ProductUOMRequest> productUOMs;
}
