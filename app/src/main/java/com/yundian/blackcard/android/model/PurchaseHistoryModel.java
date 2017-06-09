package com.yundian.blackcard.android.model;

import com.yundian.blackcard.android.util.TimeUtil;

import java.io.Serializable;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-07 15:44
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class PurchaseHistoryModel implements Serializable{


    private int tradeId;
    private String tradeNo;
    private int tradeGoodsId;
    private String tradeGoodsName;
    private String tradeGoodsNo;
    private int tradeNum;
    private double tradePrice;
    private double tradeTotalPrice;
    private double tradePayPrice;
    private int tradeStatus;
    private String tradeStatusTitle;
    private long tradeTime;

    public int getTradeId() {
        return tradeId;
    }

    public void setTradeId(int tradeId) {
        this.tradeId = tradeId;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public int getTradeGoodsId() {
        return tradeGoodsId;
    }

    public void setTradeGoodsId(int tradeGoodsId) {
        this.tradeGoodsId = tradeGoodsId;
    }

    public String getTradeGoodsName() {
        return tradeGoodsName;
    }

    public void setTradeGoodsName(String tradeGoodsName) {
        this.tradeGoodsName = tradeGoodsName;
    }

    public String getTradeGoodsNo() {
        return tradeGoodsNo;
    }

    public void setTradeGoodsNo(String tradeGoodsNo) {
        this.tradeGoodsNo = tradeGoodsNo;
    }

    public int getTradeNum() {
        return tradeNum;
    }

    public void setTradeNum(int tradeNum) {
        this.tradeNum = tradeNum;
    }

    public double getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(double tradePrice) {
        this.tradePrice = tradePrice;
    }

    public double getTradeTotalPrice() {
        return tradeTotalPrice;
    }

    public void setTradeTotalPrice(double tradeTotalPrice) {
        this.tradeTotalPrice = tradeTotalPrice;
    }

    public double getTradePayPrice() {
        return tradePayPrice;
    }

    public void setTradePayPrice(double tradePayPrice) {
        this.tradePayPrice = tradePayPrice;
    }

    public int getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(int tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getTradeStatusTitle() {
        return tradeStatusTitle;
    }

    public void setTradeStatusTitle(String tradeStatusTitle) {
        this.tradeStatusTitle = tradeStatusTitle;
    }

    public String formatTradeTime(){
        return TimeUtil.formatDate2(tradeTime);
    }
    public long getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(long tradeTime) {
        this.tradeTime = tradeTime;
    }
}
