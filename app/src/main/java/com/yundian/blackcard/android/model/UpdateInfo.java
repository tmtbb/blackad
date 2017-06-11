package com.yundian.blackcard.android.model;

/**
 * Created by yaowang on 2017/6/11.
 */

public class UpdateInfo {
    /**
     * isForce : 1
     * description : 1.测试测试1
     2.测试测试2
     3.测试测试3
     4.测试测试4
     * versionCode : 3
     * url : 1.12.apk
     * isUpdate : 1
     * version : 1.12
     */

    private int isForce;
    private String description;
    private int versionCode;
    private String url;
    private int isUpdate;
    private String version;

    public int getIsForce() {
        return isForce;
    }

    public void setIsForce(int isForce) {
        this.isForce = isForce;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(int isUpdate) {
        this.isUpdate = isUpdate;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
