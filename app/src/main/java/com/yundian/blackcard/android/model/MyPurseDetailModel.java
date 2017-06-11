package com.yundian.blackcard.android.model;

import com.yundian.blackcard.android.util.TimeUtil;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-07 11:37
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class MyPurseDetailModel {

    private double amount;//充值或消费金额
    private double balance;//原余额

    private long createTime; //时间
    private String tradeNo; //交易号

    private String tradeName; //交易名称

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public long getCreateTime() {
        return createTime;
    }

    public String formatCreateTime(){
        return TimeUtil.formatDate2(createTime);
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }
}
