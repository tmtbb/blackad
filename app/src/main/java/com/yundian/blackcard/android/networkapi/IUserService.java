package com.yundian.blackcard.android.networkapi;

import com.yundian.blackcard.android.model.AccountInfo;
import com.yundian.blackcard.android.model.BalanceModel;
import com.yundian.blackcard.android.model.MyPurseDetailModel;
import com.yundian.blackcard.android.model.PayInfo;
import com.yundian.blackcard.android.model.UserDetailModel;
import com.yundian.blackcard.android.model.UserInfo;
import com.yundian.blackcard.android.networkapi.okhttp.UserServiceImpl;
import com.yundian.comm.annotation.ServiceType;
import com.yundian.comm.networkapi.listener.OnAPIListener;

import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;

/**
 * Created by yaowang on 2017/5/11.
 */
@ServiceType(UserServiceImpl.class)
public interface IUserService {
    /**
     * 登录
     *
     * @param phoneNum
     * @param password
     * @param listener
     */
    void login(String phoneNum, String password, OnAPIListener<AccountInfo> listener);

    @ServiceType(UserServiceImpl.RetrofitUserService.class)
    Observable<AccountInfo> login(String phoneNum, String password);

    /**
     * 获取用户信息
     *
     * @param listener
     */
    void userinfo(OnAPIListener<UserInfo> listener);

    @ServiceType(UserServiceImpl.RetrofitUserService.class)
    Observable<UserInfo> userinfo();

    /**
     * 更新token
     *
     * @param listener
     */
    void refreshToken(OnAPIListener<AccountInfo> listener);

    @ServiceType(UserServiceImpl.RetrofitUserService.class)
    Observable<AccountInfo> refreshToken();


    /**
     * 账户余额
     *
     * @param listener
     */
    void balance(OnAPIListener<BalanceModel> listener);

    /**
     * 余额明细
     *
     * @param page     页码
     * @param listener
     */
    void balanceDetail(int page, OnAPIListener<List<MyPurseDetailModel>> listener);


    /**
     * 余额充值
     *
     * @param amount
     * @param payType
     * @param listener
     */
    void recharge(double amount, int payType, OnAPIListener<PayInfo> listener);

    /**
     * 用户详情
     *
     * @param listener
     */
    void userDetail(OnAPIListener<UserDetailModel> listener);

    /**
     * 用户详情
     *
     * @param listener
     */
    void userEdit(UserDetailModel model, OnAPIListener<Object> listener);


    /**
     * 修改密码
     *
     * @param password
     * @param newPassword
     * @param listener
     */
    void repassword(String password, String newPassword, OnAPIListener<Object> listener);

    /**
     * 修改支付密码
     * @param password
     * @param phoneNum
     * @param phoneCode
     * @param codeToken
     * @param listener
     */
    void rePayPassword(String password, String phoneNum, String phoneCode, String codeToken, OnAPIListener<Object> listener);

}
