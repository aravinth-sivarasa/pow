package io.pow.backend.uom;

public enum UOMMessages {
    UOM_CREATED("U20001", "UOM created successfully"),

    UOM_ALREADY_EXISTS("U40001", "UOM code already exists"),
    UOM_CODE_REQUIRED("U40002", "UOM code is required"),
    UOM_DESCRIPTION_REQUIRED("U40003", "UOM description is required"), 
    UOM_NOT_FOUND("U40004", "UOM not found"),;

    private final String code;
    private final String message;

    UOMMessages(String code, String message) {
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
