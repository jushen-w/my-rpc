package com.wjs.rpc.exception;

import com.wjs.rpc.enumeration.ExceptionCode;

public class RpcException extends RuntimeException {
    public RpcException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
    }

    public RpcException(ExceptionCode exceptionCode, String detail) {
        super(exceptionCode.getMessage() + ", " + detail);
    }

    public RpcException(ExceptionCode exceptionCode, Exception e) {
        super(exceptionCode.getMessage(), e);
    }
}
