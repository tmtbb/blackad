package com.yundian.blackcard.android.model;

/**
 * Created by yaowang on 2017/5/11.
 */

public class PayInfo {

    /**
     * sign : 01FDCF1A32399F48C292CEE078BE7369
     * tradeNo : 20170426153619916000
     * wxPayInfo : {"appId":"wx9dc39aec13ee38","timestamp":"1493192180984","package":"Sign=WXPay","partnerid":"1404391902","noncestr":"E9C45D10EDD1465B9A9137412E0429CE","prepayid":"wx20170426153619a8ead4f9420993845239","paysign":"110379D9578B531F5699482CAFABDE1B"}
     * payType : 1
     * payTotalPrice : 259
     * goodsName : 云栖会籍+名字定制(YAO BANG)
     */

    private String sign;
    private String tradeNo;
    private WXPayInfo wxPayInfo;
    private AliPayInfo aliPayInfo;
    private int payType;
    private Double payTotalPrice;
    private String goodsName;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public Double getPayTotalPrice() {
        return payTotalPrice;
    }

    public void setPayTotalPrice(Double payTotalPrice) {
        this.payTotalPrice = payTotalPrice;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public WXPayInfo getWxPayInfo() {
        return wxPayInfo;
    }

    public PayInfo setWxPayInfo(WXPayInfo wxPayInfo) {
        this.wxPayInfo = wxPayInfo;
        return this;
    }

    public AliPayInfo getAliPayInfo() {
        return aliPayInfo;
    }

    public PayInfo setAliPayInfo(AliPayInfo aliPayInfo) {
        this.aliPayInfo = aliPayInfo;
        return this;
    }
}
