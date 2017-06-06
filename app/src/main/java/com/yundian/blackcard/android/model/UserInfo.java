package com.yundian.blackcard.android.model;

import java.io.Serializable;

/**
 * Created by yaowang on 2017/5/11.
 */

public class UserInfo implements Serializable {


    /**
     * blackCardNo : 88860004
     * phoneNum : xxxxxx
     * blackCardId : 10001
     * userId : 10001
     * name : xxxx
     * blackCardName : 云栖会籍
     * blackcardCreditline : 0
     * blackCardCustomName : YAO BANG
     */

    private String blackCardNo;
    private String phoneNum;
    private Integer blackCardId;
    private Integer userId;
    private String name;
    private String blackCardName;
    private Double blackcardCreditline;
    private String blackCardCustomName;

    public String getBlackCardNo() {
        return blackCardNo;
    }

    public void setBlackCardNo(String blackCardNo) {
        this.blackCardNo = blackCardNo;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Integer getBlackCardId() {
        return blackCardId;
    }

    public void setBlackCardId(Integer blackCardId) {
        this.blackCardId = blackCardId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBlackCardName() {
        return blackCardName;
    }

    public void setBlackCardName(String blackCardName) {
        this.blackCardName = blackCardName;
    }

    public Double getBlackcardCreditline() {
        return blackcardCreditline;
    }

    public void setBlackcardCreditline(Double blackcardCreditline) {
        this.blackcardCreditline = blackcardCreditline;
    }

    public String getBlackCardCustomName() {
        return blackCardCustomName;
    }

    public void setBlackCardCustomName(String blackCardCustomName) {
        this.blackCardCustomName = blackCardCustomName;
    }
}
