package io.pow.backend.product;

public enum ProductMessages {
    PRODUCT_CREATED("P20001", "Product created successfully"),
    PRODUCT_UPDATED("P20002", "Product updated successfully"),
    PRODUCT_DELETED("P20003", "Product deleted successfully"),
    PRODUCT_NOT_FOUND("P40001", "Product not found"),
    PRODUCT_CODE_REQUIRED("P40002", "Product code is required"),
    PRODUCT_DESCRIPTION_REQUIRED("P40003", "Product description is required"),
    PRODUCT_ALREADY_EXISTS("P40004", "Product code already exists");

    private final String code;
    private final String message;

    ProductMessages(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    public String getCode() {
        return code;
    }
    
}
