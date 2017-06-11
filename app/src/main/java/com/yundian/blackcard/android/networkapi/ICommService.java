package com.yundian.blackcard.android.networkapi;

import com.yundian.blackcard.android.model.DeviceInfo;
import com.yundian.blackcard.android.model.SMSCode;
import com.yundian.blackcard.android.model.UploadInfo;
import com.yundian.comm.networkapi.listener.OnAPIListener;

import java.util.List;
import java.util.Map;

/**
 * Created by yaowang on 2017/5/11.
 */

public interface ICommService {
    void deviceRegister(DeviceInfo deviceInfo, OnAPIListener<DeviceInfo> listener);

    void smsCode(String phoneNum, Integer type, OnAPIListener<SMSCode> listener);

    void validate(String phoneNum, String codeToken, String phoneCode, int codeType, OnAPIListener<Object> listener);

    void upload(List<String> paths, OnAPIListener<List<UploadInfo>> listener);

    void sysLog(String event, Map<String,Object> map);
}
