package com.yundian.comm.networkapi.response;

/**
 * Created by yaowang on 2017/5/3.
 */

public class DefResponse<T> {

    public static final int SUCCESS_CODE = 0;
    private  Integer status;
    private  String  msg;
    private  T data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        this.data = data;
    }

}

