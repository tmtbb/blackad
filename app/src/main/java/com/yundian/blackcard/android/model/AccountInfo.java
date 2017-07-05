package com.yundian.blackcard.android.model;

/**
 * Created by yaowang on 2017/5/11.
 */

public class AccountInfo {
    private String token;
    private int isPay;

    public String getToken() {
        return token;
    }

    public AccountInfo setToken(String token) {
        this.token = token;
        return this;
    }

    public int getIsPay() {
        return isPay;
    }

    public void setIsPay(int isPay) {
        this.isPay = isPay;
    }
}
