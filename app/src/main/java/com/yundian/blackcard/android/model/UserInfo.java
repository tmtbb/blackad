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

    private Long userId;
    private String phoneNum;
    private String name;
    private String fullName;
    private String blackCardName;
    private Integer blackCardId;
    private String blackCardNo;
    private String nickName;
    private String headUrl;
    private String blackCardCustomName;
    private Double blackcardCreditline;
    private Double balance;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBlackCardName() {
        return blackCardName;
    }

    public void setBlackCardName(String blackCardName) {
        this.blackCardName = blackCardName;
    }

    public Integer getBlackCardId() {
        return blackCardId;
    }

    public void setBlackCardId(Integer blackCardId) {
        this.blackCardId = blackCardId;
    }

    public String getBlackCardNo() {
        return blackCardNo;
    }

    public void setBlackCardNo(String blackCardNo) {
        this.blackCardNo = blackCardNo;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getBlackCardCustomName() {
        return blackCardCustomName;
    }

    public void setBlackCardCustomName(String blackCardCustomName) {
        this.blackCardCustomName = blackCardCustomName;
    }

    public Double getBlackcardCreditline() {
        return blackcardCreditline;
    }

    public void setBlackcardCreditline(Double blackcardCreditline) {
        this.blackcardCreditline = blackcardCreditline;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
