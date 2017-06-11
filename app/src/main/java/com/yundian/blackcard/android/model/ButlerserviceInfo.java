package com.yundian.blackcard.android.model;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-09 14:25
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class ButlerserviceInfo {

    private long createTime;
    private long serviceStartTime;
    private long serviceEndTime;
    private String serviceStatusTitle;
    private int serviceStatus;
    private String serviceDetails;
    private String serviceUserTel;
    private double serviceAmount;
    private String serviceName;
    private int serviceType;
    private String serviceNo;


    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceUserTel() {
        return serviceUserTel;
    }

    public void setServiceUserTel(String serviceUserTel) {
        this.serviceUserTel = serviceUserTel;
    }

    public String getServiceNo() {
        return serviceNo;
    }

    public void setServiceNo(String serviceNo) {
        this.serviceNo = serviceNo;
    }

    public String getServiceDetails() {
        return serviceDetails;
    }

    public void setServiceDetails(String serviceDetails) {
        this.serviceDetails = serviceDetails;
    }

    public double getServiceAmount() {
        return serviceAmount;
    }

    public void setServiceAmount(double serviceAmount) {
        this.serviceAmount = serviceAmount;
    }

    public int getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(int serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getServiceStatusTitle() {
        return serviceStatusTitle;
    }

    public void setServiceStatusTitle(String serviceStatusTitle) {
        this.serviceStatusTitle = serviceStatusTitle;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getServiceStartTime() {
        return serviceStartTime;
    }

    public void setServiceStartTime(long serviceStartTime) {
        this.serviceStartTime = serviceStartTime;
    }

    public long getServiceEndTime() {
        return serviceEndTime;
    }

    public void setServiceEndTime(long serviceEndTime) {
        this.serviceEndTime = serviceEndTime;
    }
}
