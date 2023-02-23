package com.wjs.rpc.test;

import java.io.Serializable;

public class RandomObject implements Serializable {
    private Integer id;

    private String data;

    public RandomObject(Integer id, String data) {
        this.id = id;
        this.data = data;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
