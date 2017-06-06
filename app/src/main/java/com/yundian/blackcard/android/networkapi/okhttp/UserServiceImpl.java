package com.yundian.blackcard.android.networkapi.okhttp;

import com.yundian.blackcard.android.model.AccountInfo;
import com.yundian.blackcard.android.model.UserInfo;
import com.yundian.blackcard.android.networkapi.IUserService;
import com.yundian.comm.networkapi.listener.OnAPIListener;
import com.yundian.comm.networkapi.obsserver.DefObserver;
import com.yundian.comm.networkapi.okhttp.OkHttpService;
import com.yundian.comm.networkapi.response.DefResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
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
    }

    @Override
    public void login(String phoneNum, String password, OnAPIListener<AccountInfo> listener) {
        setSubscribe(service.login(phoneNum, password),new DefObserver<AccountInfo>(listener));
    }



    @Override
    public void userinfo(OnAPIListener<UserInfo> listener) {
        setSubscribe(service.userinfo(),new DefObserver<UserInfo>(listener));
    }



    @Override
    public void refreshToken(OnAPIListener<AccountInfo> listener) {
        setSubscribe(service.refreshToken(),new DefObserver<AccountInfo>(listener));
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
}
