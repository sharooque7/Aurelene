package com.ainzson.usermanagementservice.api;

public enum ResultCode implements IErrorCode {

    SUCCESS(200, "Operation successful"),
    FAILED(500, "Operation failed"),
    VALIDATE_FAILED(404, "Parameter validation failed"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    CONFLICT(409, "Conflict");

    private long code;
    private  String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return  this.code;
    }

    public String getMessage() {
        return  this.message;
    }
}
