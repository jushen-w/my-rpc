package com.wjs.rpc.dto;

import com.wjs.rpc.enumeration.StatusCode;

import java.io.Serializable;

public class RpcResp<T> implements Serializable {
    private Integer statusCode;

    private String statusMessage;

    private T data;

    public static <T> RpcResp<T> success(T data) {
        RpcResp<T> response = new RpcResp<>();
        response.setStatusCode(StatusCode.SUCCESS.getCode());
        response.setStatusMessage(StatusCode.SUCCESS.getMessage());
        response.setData(data);
        return response;
    }

    public static <T> RpcResp<T> fail(StatusCode code) {
        RpcResp<T> response = new RpcResp<>();
        response.setStatusCode(code.getCode());
        response.setStatusMessage(code.getMessage());
        return response;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
