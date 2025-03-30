package io.pow.backend.uom;

public class UOMException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String code;
    public UOMException(UOMMessages message) {
        super(message.getMessage());
        this.code = message.getCode();
    }

    public String getCode() {
        return code;
    }
}