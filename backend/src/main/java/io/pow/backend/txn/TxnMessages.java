package io.pow.backend.txn;

public enum TxnMessages {
    TXN_CREATED("T20001", "Transaction created successfully"),
    PRODUCT_CODE_REQUIRED("T40001", "Product code is required");

    private final String message;

    TxnMessages(String code, String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
