package com.yundian.blackcard.android.model;

/**
 * Created by yaowang on 2017/5/11.
 */

public class DeviceInfo {
    /**
     * deviceKeyId : 34474661562457
     * deviceKey : 24BFA1509B794899834AA9E24B447311
     */

    private String deviceId;
    private String deviceModel;
    private String deviceResolution;
    private String deviceName;
    private String osVersion;

    private Long deviceKeyId;
    private String deviceKey;

    public Long getDeviceKeyId() {
        return deviceKeyId;
    }

    public DeviceInfo setDeviceKeyId(Long deviceKeyId) {
        this.deviceKeyId = deviceKeyId;
        return this;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public DeviceInfo setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
        return this;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public DeviceInfo setDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public DeviceInfo setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
        return this;
    }

    public String getDeviceResolution() {
        return deviceResolution;
    }

    public DeviceInfo setDeviceResolution(String deviceResolution) {
        this.deviceResolution = deviceResolution;
        return this;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public DeviceInfo setDeviceName(String deviceName) {
        this.deviceName = deviceName;
        return this;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public DeviceInfo setOsVersion(String osVersion) {
        this.osVersion = osVersion;
        return this;
    }
}
