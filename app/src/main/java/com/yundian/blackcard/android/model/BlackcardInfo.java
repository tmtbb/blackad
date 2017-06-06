package com.yundian.blackcard.android.model;

import java.io.Serializable;

/**
 * Created by yaowang on 2017/5/11.
 */

public class BlackcardInfo implements Serializable {

    /**
     * blackcardPrice : 199
     * blackcardName : 云栖会籍
     * blackcardId : 10001
     */

    private Double blackcardPrice;
    private String blackcardName;
    private Long blackcardId;

    public Double getBlackcardPrice() {
        return blackcardPrice;
    }

    public void setBlackcardPrice(Double blackcardPrice) {
        this.blackcardPrice = blackcardPrice;
    }

    public String getBlackcardName() {
        return blackcardName;
    }

    public void setBlackcardName(String blackcardName) {
        this.blackcardName = blackcardName;
    }

    public Long getBlackcardId() {
        return blackcardId;
    }

    public void setBlackcardId(Long blackcardId) {
        this.blackcardId = blackcardId;
    }
}
