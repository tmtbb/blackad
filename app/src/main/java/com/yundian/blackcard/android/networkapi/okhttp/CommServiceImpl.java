package com.yundian.blackcard.android.networkapi.okhttp;

import com.yundian.blackcard.android.model.DeviceInfo;
import com.yundian.blackcard.android.model.SMSCode;
import com.yundian.blackcard.android.networkapi.ICommService;
import com.yundian.comm.networkapi.listener.OnAPIListener;
import com.yundian.comm.networkapi.obsserver.DefObserver;
import com.yundian.comm.networkapi.okhttp.OkHttpService;
import com.yundian.comm.networkapi.response.DefResponse;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by yaowang on 2017/5/11.
 */

public class CommServiceImpl extends OkHttpService<CommServiceImpl.RetrofitCommService> implements ICommService {

    public    interface RetrofitCommService {
        @FormUrlEncoded
        @POST("/api/device/register.json")
        Observable<DefResponse<DeviceInfo>> deviceRegister(@FieldMap Map<String,Object> map);
        @FormUrlEncoded
        @POST("/api/sms/code.json")
        Observable<DefResponse<DeviceInfo>> smsCode(@Field("phoneNum") String phoneNum,@Field("type") Integer type );

    }

    @Override
    public void deviceRegister(DeviceInfo deviceInfo,OnAPIListener<DeviceInfo> listener) {
        Map<String,Object> deviceInfoMap = new HashMap<String,Object>();
        deviceInfoMap.put("deviceId",deviceInfo.getDeviceId());
        deviceInfoMap.put("deviceModel",deviceInfo.getDeviceModel());
        deviceInfoMap.put("deviceName",deviceInfo.getDeviceName());
        deviceInfoMap.put("deviceResolution",deviceInfo.getDeviceResolution());
        setSubscribe(service.deviceRegister(deviceInfoMap),new DefObserver<DeviceInfo>(listener));
    }

    @Override
    public void smsCode(String phoneNum, Integer type, OnAPIListener<SMSCode> listener) {
        setSubscribe(service.smsCode(phoneNum, type),new DefObserver<SMSCode>(listener));
    }
}
