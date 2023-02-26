package com.wjs.rpc.enumeration;

public enum ExceptionCode {
    INVOCATION_FAILED(500, "Invocation failed!"),
    SERVICE_NOT_FOUND(501, "Service is not found!"),
    SERVICE_NOT_REGISTERED(502, "Service is not registered!");

    private final int code;
    private final String message;
    private ExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
