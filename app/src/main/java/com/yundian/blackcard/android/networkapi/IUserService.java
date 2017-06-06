package com.yundian.blackcard.android.networkapi;

import com.yundian.blackcard.android.model.AccountInfo;
import com.yundian.blackcard.android.model.UserInfo;
import com.yundian.blackcard.android.networkapi.okhttp.UserServiceImpl;
import com.yundian.comm.annotation.ServiceType;
import com.yundian.comm.networkapi.listener.OnAPIListener;

import io.reactivex.Observable;

/**
 * Created by yaowang on 2017/5/11.
 */
@ServiceType(UserServiceImpl.class)
public interface IUserService {
    void login(String phoneNum, String password, OnAPIListener<AccountInfo> listener);
    void userinfo(OnAPIListener<UserInfo> listener);
    void refreshToken(OnAPIListener<AccountInfo> listener);
    @ServiceType(UserServiceImpl.RetrofitUserService.class)
    Observable<AccountInfo> login(String phoneNum, String password);

    @ServiceType(UserServiceImpl.RetrofitUserService.class)
    Observable<UserInfo> userinfo();

    @ServiceType(UserServiceImpl.RetrofitUserService.class)
    Observable<AccountInfo> refreshToken();


}
