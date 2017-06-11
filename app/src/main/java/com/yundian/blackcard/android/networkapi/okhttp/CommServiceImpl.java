package com.yundian.blackcard.android.networkapi.okhttp;

import com.yundian.blackcard.android.model.DeviceInfo;
import com.yundian.blackcard.android.model.SMSCode;
import com.yundian.blackcard.android.model.UploadInfo;
import com.yundian.blackcard.android.networkapi.ICommService;
import com.yundian.comm.networkapi.listener.OnAPIListener;
import com.yundian.comm.networkapi.obsserver.DefObserver;
import com.yundian.comm.networkapi.okhttp.OkHttpService;
import com.yundian.comm.networkapi.response.DefResponse;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by yaowang on 2017/5/11.
 */

public class CommServiceImpl extends OkHttpService<CommServiceImpl.RetrofitCommService> implements ICommService {

    public interface RetrofitCommService {
        @FormUrlEncoded
        @POST("/api/device/register.json")
        Observable<DefResponse<DeviceInfo>> deviceRegister(@FieldMap Map<String, Object> map);

        @FormUrlEncoded
        @POST("/api/sms/code.json")
        Observable<DefResponse<SMSCode>> smsCode(@Field("phoneNum") String phoneNum, @Field("codeType") Integer type);


        @FormUrlEncoded
        @POST("/api/sms/code/validate.json")
        Observable<DefResponse<Object>> validate(@Field("phoneNum") String phoneNum, @Field("codeToken") String codeToken, @Field("phoneCode") String phoneCode, @Field("codeType") int codeType);

        @Multipart
        @POST("/api/file/upload.json")
        Observable<DefResponse<List<UploadInfo>>> upload(@PartMap Map<String, RequestBody> map);

        @FormUrlEncoded
        @POST("/api/sys/log.josn")
        Observable<DefResponse<Object>> sysLog(@Field("event") String event, @FieldMap Map<String, Object> map);
    }

    @Override
    public void deviceRegister(DeviceInfo deviceInfo, OnAPIListener<DeviceInfo> listener) {
        Map<String, Object> deviceInfoMap = new HashMap<String, Object>();
        deviceInfoMap.put("deviceId", deviceInfo.getDeviceId());
        deviceInfoMap.put("deviceModel", deviceInfo.getDeviceModel());
        deviceInfoMap.put("deviceName", deviceInfo.getDeviceName());
        deviceInfoMap.put("deviceResolution", deviceInfo.getDeviceResolution());
        setSubscribe(service.deviceRegister(deviceInfoMap), new DefObserver<DeviceInfo>(listener));
    }

    @Override
    public void smsCode(String phoneNum, Integer type, OnAPIListener<SMSCode> listener) {
        setSubscribe(service.smsCode(phoneNum, type), new DefObserver<SMSCode>(listener));
    }

    @Override
    public void validate(String phoneNum, String codeToken, String phoneCode, int codeType, OnAPIListener<Object> listener) {
        setSubscribe(service.validate(phoneNum, codeToken, phoneCode, codeType), new DefObserver<Object>(listener));
    }

    @Override
    public void upload(List<String> paths, OnAPIListener<List<UploadInfo>> listener) {
        Map<String, RequestBody> map = new HashMap<>();
        for (int i = 0; i < paths.size(); i++) {
            File file = new File(paths.get(i));
            map.put("file\"; filename=", RequestBody.create(MediaType.parse("image/jpeg"), file));
        }
        setSubscribe(service.upload(map), new DefObserver<List<UploadInfo>>(listener));
    }

    @Override
    public void sysLog(String event, Map<String, Object> map) {
        setSubscribe(service.sysLog(event, map), new DefObserver<Object>(null));
    }
}
