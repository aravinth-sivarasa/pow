package io.pow.backend.txn;

public class TxnException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    private final String code;

    public TxnException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
