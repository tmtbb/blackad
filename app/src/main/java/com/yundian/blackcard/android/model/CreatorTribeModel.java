package com.yundian.blackcard.android.model;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-21 10:22
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class CreatorTribeModel extends BaseModel {
    private int status;
    private String id;
    private int verifyNum;

    public int getStatus() {
        return status;
    }

    public CreatorTribeModel setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVerifyNum() {
        return verifyNum;
    }

    public CreatorTribeModel setVerifyNum(int verifyNum) {
        this.verifyNum = verifyNum;
        return this;
    }
}
