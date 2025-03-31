package io.pow.backend.product;

public enum ProductMessages {
    PRODUCT_CREATED("P20001", "Product created successfully"),
    PRODUCT_UOM_CREATED("P20002", "Product UOM created successfully"),
    PRODUCT_NOT_FOUND("P40001", "Product not found"),
    PRODUCT_CODE_REQUIRED("P40002", "Product code is required"),
    PRODUCT_DESCRIPTION_REQUIRED("P40003", "Product description is required"),
    PRODUCT_ALREADY_EXISTS("P40004", "Product code already exists"),
    PRODUCT_JSON_PARSE("P40005", "Problem with JSON parsing"), 
    PRODUCT_UOM_REQUIRED("P40006", "At least one UOM is required"),
    PRODUCT_UOM_NOT_FOUND("P40007", "Product or Unit of Measure not found"),;
     

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
