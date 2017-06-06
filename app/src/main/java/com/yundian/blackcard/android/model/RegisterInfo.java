package com.yundian.blackcard.android.model;

import java.io.Serializable;

/**
 * Created by yaowang on 2017/5/11.
 */

public class RegisterInfo implements Serializable {
    /*
        blackcardId	是	int	黑卡id
        phoneNum	是	string	手机号
        email	是	string	邮箱
        fullName	是	string	真实姓名
        customName	否	string	定制姓名名称为空为不定制
        identityCard	是	string	身份证号
        addr	是	string	地址
        addr1	是	string	备用地址
        province	是	string	省份
        city	是	string	城市
    */
    private BlackcardInfo blackcardInfo;
    private String phoneNum;
    private String email;
    private String fullName;
    private String customName;
    private String identityCard;
    private String addr;
    private String addr1;
    private String province;
    private String city;
    private Double customNamePrice;


    public BlackcardInfo getBlackcardInfo() {
        return blackcardInfo;
    }

    public RegisterInfo setBlackcardInfo(BlackcardInfo blackcardInfo) {
        this.blackcardInfo = blackcardInfo;
        return this;
    }

    public Double getCustomNamePrice() {
        return customNamePrice;
    }

    public RegisterInfo setCustomNamePrice(Double customNamePrice) {
        this.customNamePrice = customNamePrice;
        return this;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public RegisterInfo setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RegisterInfo setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public RegisterInfo setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getCustomName() {
        return customName;
    }

    public RegisterInfo setCustomName(String customName) {
        this.customName = customName;
        return this;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public RegisterInfo setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
        return this;
    }

    public String getAddr() {
        return addr;
    }

    public RegisterInfo setAddr(String addr) {
        this.addr = addr;
        return this;
    }

    public String getAddr1() {
        return addr1;
    }

    public RegisterInfo setAddr1(String addr1) {
        this.addr1 = addr1;
        return this;
    }

    public String getProvince() {
        return province;
    }

    public RegisterInfo setProvince(String province) {
        this.province = province;
        return this;
    }

    public String getCity() {
        return city;
    }

    public RegisterInfo setCity(String city) {
        this.city = city;
        return this;
    }
}
