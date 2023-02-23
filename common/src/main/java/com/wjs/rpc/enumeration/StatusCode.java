package com.wjs.rpc.enumeration;

public enum StatusCode {
    SUCCESS(200, "The process call was successful."),
    FAIL(500, "The process call was failed.");

    private final int code;
    private final String message;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
