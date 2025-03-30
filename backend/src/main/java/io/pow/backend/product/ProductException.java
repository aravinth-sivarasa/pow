package io.pow.backend.product;

public class ProductException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String code;
    public ProductException(ProductMessages message) {
        super(message.getMessage());
        this.code = message.getCode();
    }

    public String getCode() {
        return code;
    }
}