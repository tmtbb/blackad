package com.yundian.blackcard.android.model;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-21 10:21
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class TribeListModel {

    private String id;
    private String name;
    private String industry;
    private String description;
    private int memberNum;

    //
    private int verifyNum;
    //0 1 2
    private int type ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(int memberNum) {
        this.memberNum = memberNum;
    }

    public int getVerifyNum() {
        return verifyNum;
    }

    public void setVerifyNum(int verifyNum) {
        this.verifyNum = verifyNum;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
