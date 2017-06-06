package com.yundian.blackcard.android.model;
import com.google.gson.annotations.SerializedName;
/**
 * Created by yaowang on 2017/5/11.
 */

public class WXPayInfo {
    /**
     * appId : wx9dc39aec13ee38
     * timestamp : 1493192180984
     * package : Sign=WXPay
     * partnerid : 1404391902
     * noncestr : E9C45D10EDD1465B9A9137412E0429CE
     * prepayid : wx20170426153619a8ead4f9420993845239
     * paysign : 110379D9578B531F5699482CAFABDE1B
     */

    private String appid;
    private String timestamp;
    @SerializedName("package")
    private String payPackage;
    private String partnerid;
    private String noncestr;
    private String prepayid;
    private String paysign;

    public String getAppId() {
        return appid;
    }

    public void setAppId(String appId) {
        this.appid = appId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPayPackage() {
        return payPackage;
    }

    public WXPayInfo setPayPackage(String payPackage) {
        this.payPackage = payPackage;
        return this;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPaysign() {
        return paysign;
    }

    public void setPaysign(String paysign) {
        this.paysign = paysign;
    }
}
