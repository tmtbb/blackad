package com.yundian.blackcard.android.networkapi.okhttp;

import com.yundian.blackcard.android.model.AccountInfo;
import com.yundian.blackcard.android.model.BlackcardInfos;
import com.yundian.blackcard.android.model.PayInfo;
import com.yundian.blackcard.android.model.RegisterInfo;
import com.yundian.blackcard.android.model.SMSCode;
import com.yundian.blackcard.android.networkapi.IBlackcardService;
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

public class BlackcardServiceImpl extends OkHttpService<BlackcardServiceImpl.RetrofitBlackcardService> implements IBlackcardService {

    public interface RetrofitBlackcardService {

        @FormUrlEncoded
        @POST("/api/user/login.json")
        Observable<DefResponse<AccountInfo>> login(@Field("phoneNum") String phoneNum, @Field("type") String password);

        @POST("/api/blackcard/infos.json")
        Observable<DefResponse<BlackcardInfos>> blackcardInfos();

        @FormUrlEncoded
        @POST("/api/blackcard/register/pay.json")
        Observable<DefResponse<PayInfo>> tokenPay(@Field("token") String token);

        @FormUrlEncoded
        @POST("/api/blackcard/register/pay.json")
        Observable<DefResponse<PayInfo>> smsCodePay(@FieldMap Map<String, Object> map);

        @FormUrlEncoded
        @POST("/api/blackcard/sms/code.json")
        Observable<DefResponse<SMSCode>> smsCode(@Field("blackcardNo") String blackcardNo);

        @FormUrlEncoded
        @POST("/api/blackcard/repassword.json")
        Observable<DefResponse<Object>> rePassword(@FieldMap Map<String, Object> map);


        @FormUrlEncoded
        @POST("/api/blackcard/register.json")
        Observable<DefResponse<AccountInfo>> register(@FieldMap Map<String, Object> map);
    }

    @Override
    public void blackcardInfos(OnAPIListener<BlackcardInfos> listener) {
        setSubscribe(service.blackcardInfos(), new DefObserver<BlackcardInfos>(listener));
    }

    @Override
    public void register(RegisterInfo registerInfo, OnAPIListener<AccountInfo> listener) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("phoneNum", registerInfo.getPhoneNum());
        map.put("blackcardId", registerInfo.getBlackcardInfo().getBlackcardId());
        map.put("email", registerInfo.getEmail());
        map.put("fullName", registerInfo.getFullName());
        if( registerInfo.getCustomName() != null) {
            map.put("customName", registerInfo.getCustomName());
        }
        map.put("identityCard", registerInfo.getIdentityCard());
        map.put("addr", registerInfo.getAddr());
        map.put("addr1", registerInfo.getAddr1());
        map.put("province", registerInfo.getProvince());
        map.put("city", registerInfo.getCity());
        setSubscribe(service.register(map), new DefObserver<AccountInfo>(listener));
    }

    @Override
    public void tokenPay(String token, OnAPIListener<PayInfo> listener) {
        setSubscribe(service.tokenPay(token), new DefObserver<PayInfo>(listener));
    }

    @Override
    public void smsCodePay(SMSCode smsCode, OnAPIListener<PayInfo> listener) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("codeToken", smsCode.getCodeToken());
        map.put("phoneCode", smsCode.getPhoneCode());
        map.put("phoneNum", smsCode.getPhoneNum());
        setSubscribe(service.smsCodePay(map), new DefObserver<PayInfo>(listener));
    }

    @Override
    public void rePassword(String blackcardNo, SMSCode smsCode, String password, OnAPIListener<Object> listener) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("codeToken", smsCode.getCodeToken());
        map.put("phoneCode", smsCode.getPhoneCode());
        map.put("blackcardNo", blackcardNo);
        map.put("password", password);
        setSubscribe(service.rePassword(map), new DefObserver<Object>(listener));

    }

    @Override
    public void smsCode(String blackcardNo, OnAPIListener<SMSCode> listener) {
        setSubscribe(service.smsCode(blackcardNo), new DefObserver<SMSCode>(listener));
    }
}
