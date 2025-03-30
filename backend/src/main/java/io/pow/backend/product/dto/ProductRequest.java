package io.pow.backend.product.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProductRequest {
    private String code;
    private String description;
    private Long productId;
    private List<ProductUOMRequest> productUOMs;
}
