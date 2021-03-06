package com.huanzong.property.database;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DataBase<T> {

    private int code;
    private String msg;
    @SerializedName("data")
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        if (data == null||data.equals("")){
//            this.data = null;
            return;
        }else {
            this.data = data;
        }

    }
}
