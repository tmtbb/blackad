package com.yundian.blackcard.android.networkapi;

import com.yundian.blackcard.android.model.DeviceInfo;
import com.yundian.blackcard.android.model.SMSCode;
import com.yundian.comm.networkapi.listener.OnAPIListener;

/**
 * Created by yaowang on 2017/5/11.
 */

public interface ICommService {
    void deviceRegister(DeviceInfo deviceInfo,OnAPIListener<DeviceInfo> listener);
    void smsCode(String phoneNum,Integer type,OnAPIListener<SMSCode> listener);
}
