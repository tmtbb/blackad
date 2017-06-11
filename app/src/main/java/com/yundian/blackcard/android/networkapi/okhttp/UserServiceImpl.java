package com.yundian.blackcard.android.networkapi.okhttp;

import android.text.TextUtils;

import com.yundian.blackcard.android.model.AccountInfo;
import com.yundian.blackcard.android.model.BalanceModel;
import com.yundian.blackcard.android.model.MyPurseDetailModel;
import com.yundian.blackcard.android.model.PayInfo;
import com.yundian.blackcard.android.model.UserDetailModel;
import com.yundian.blackcard.android.model.UserInfo;
import com.yundian.blackcard.android.networkapi.IUserService;
import com.yundian.comm.networkapi.listener.OnAPIListener;
import com.yundian.comm.networkapi.obsserver.DefObserver;
import com.yundian.comm.networkapi.okhttp.OkHttpService;
import com.yundian.comm.networkapi.response.DefResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by yaowang on 2017/5/11.
 */

public class UserServiceImpl extends OkHttpService<UserServiceImpl.RetrofitUserService> implements IUserService {

    public interface RetrofitUserService {

        @FormUrlEncoded
        @POST("/api/user/login.json")
        Observable<DefResponse<AccountInfo>> login(@Field("phoneNum") String phoneNum, @Field("password") String password);

        @POST("/api/user/info.json")
        Observable<DefResponse<UserInfo>> userinfo();

        @POST("/api/user/refreshToken.json")
        Observable<DefResponse<AccountInfo>> refreshToken();

        @POST("/api/user/balance.json")
        Observable<DefResponse<BalanceModel>> balance();

        @FormUrlEncoded
        @POST("/api/user/balance/details.json")
        Observable<DefResponse<List<MyPurseDetailModel>>> balanceDetail(@Field("page") int page);

        @FormUrlEncoded
        @POST("/api/user/balance/recharge.json")
        Observable<DefResponse<PayInfo>> recharge(@Field("amount") double amount, @Field("payType") int payType);

        @POST("/api/user/detail.json")
        Observable<DefResponse<UserDetailModel>> userDetail();

        @FormUrlEncoded
        @POST("/api/user/edit.json")
        Observable<DefResponse<PayInfo>> userEdit(@FieldMap Map<String, Object> model);

        @FormUrlEncoded
        @POST("/api/user/repassword.json")
        Observable<DefResponse<PayInfo>> repassword(@Field("password") String password, @Field("newPassword") String newPassword);

        @FormUrlEncoded
        @POST("/api/user/pay/repassword.json")
        Observable<DefResponse<PayInfo>> rePayPassword(@Field("password") String password, @Field("phoneNum") String phoneNum, @Field("phoneCode") String phoneCode, @Field("codeToken") String codeToken);

    }

    @Override
    public void login(String phoneNum, String password, OnAPIListener<AccountInfo> listener) {
        setSubscribe(service.login(phoneNum, password), new DefObserver<AccountInfo>(listener));
    }


    @Override
    public void userinfo(OnAPIListener<UserInfo> listener) {
        setSubscribe(service.userinfo(), new DefObserver<UserInfo>(listener));
    }


    @Override
    public void refreshToken(OnAPIListener<AccountInfo> listener) {
        setSubscribe(service.refreshToken(), new DefObserver<AccountInfo>(listener));
    }

    @Override
    public Observable<AccountInfo> login(String phoneNum, String password) {
        return null;
    }

    @Override
    public Observable<UserInfo> userinfo() {
        return null;
    }

    @Override
    public Observable<AccountInfo> refreshToken() {
        return null;
    }

    @Override
    public void balance(OnAPIListener<BalanceModel> listener) {
        setSubscribe(service.balance(), new DefObserver<BalanceModel>(listener));
    }

    @Override
    public void balanceDetail(int page, OnAPIListener<List<MyPurseDetailModel>> listener) {
        setSubscribe(service.balanceDetail(page), new DefObserver<List<MyPurseDetailModel>>(listener));
    }

    @Override
    public void recharge(double amount, int payType, OnAPIListener<PayInfo> listener) {
        setSubscribe(service.recharge(amount, payType), new DefObserver<PayInfo>(listener));
    }

    @Override
    public void userDetail(OnAPIListener<UserDetailModel> listener) {
        setSubscribe(service.userDetail(), new DefObserver<UserDetailModel>(listener));
    }

    @Override
    public void userEdit(UserDetailModel model, OnAPIListener<Object> listener) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("position", model.getPosition());
        map.put("company", model.getCompany());
        map.put("sex", model.getSex());
        map.put("identityCard", model.getIdentityCard());
        map.put("email", model.getEmail());
        map.put("nickName", model.getNickName());
        if (!TextUtils.isEmpty(model.getHeadUrl()))
            map.put("headUrl", model.getHeadUrl());

        setSubscribe(service.userEdit(map), new DefObserver<Object>(listener));
    }

    @Override
    public void repassword(String password, String newPassword, OnAPIListener<Object> listener) {
        setSubscribe(service.repassword(password, newPassword), new DefObserver<Object>(listener));
    }

    @Override
    public void rePayPassword(String password, String phoneNum, String phoneCode, String codeToken, OnAPIListener<Object> listener) {
        setSubscribe(service.rePayPassword(password, phoneNum, phoneCode, codeToken), new DefObserver<Object>(listener));
    }
}
