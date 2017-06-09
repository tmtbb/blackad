package com.yundian.blackcard.android.model;

import java.io.Serializable;

/**
 * Created by yaowang on 2017/5/11.
 */

public class SMSCode implements Serializable{
    private String codeToken;
    private String phoneNum;
    private String phoneCode;

    public String getPhoneNum() {
        return phoneNum;
    }

    public SMSCode setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
        return this;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public SMSCode setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
        return this;
    }

    public String getCodeToken() {
        return codeToken;
    }

    public SMSCode setCodeToken(String codeToken) {
        this.codeToken = codeToken;
        return this;
    }
}
