package com.yundian.blackcard.android.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.manager.CurrentUserManager;
import com.yundian.blackcard.android.model.AccountInfo;
import com.yundian.blackcard.android.model.UserInfo;
import com.yundian.blackcard.android.networkapi.NetworkAPIFactory;
import com.yundian.comm.networkapi.obsserver.BaseObserver;
import com.yundian.comm.util.SPUtils;
import com.yundian.comm.util.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

public class LoginActivity extends BaseActivity {


    private static String UserToken = "UserToken";
    @BindView(R.id.phone)
    protected EditText phone;
    @BindView(R.id.password)
    protected EditText password;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }


    @Override
    public void initData() {
        super.initData();

        if (SPUtils.contains(this, UserToken)) {
            reqeust(NetworkAPIFactory.getUserService().refreshToken());
        }
    }

    @OnClick({R.id.but_register, R.id.but_repassword})
    protected void onClick(View view) {

        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.but_register:
                intent.setClass(this, RegisterActivity.class);
                break;
            case R.id.but_repassword:
                intent.setClass(this, RePasswordActivity.class);
                break;
        }
        startActivity(intent);
    }


    /**
     * 登录事件
     */
    @OnClick(R.id.login_button)
    protected void onClickLogin() {

        String strPhone = phone.getText().toString().trim();
        String strPassword = password.getText().toString().trim();
        if (StringUtils.isEmpty(strPhone)) {
            showToast(phone.getHint());
        } else if (StringUtils.isEmpty(strPassword)) {
            showToast(password.getHint());
        } else {
            reqeust(NetworkAPIFactory.getUserService().login(strPhone, strPassword));
        }
    }


    private void reqeust(Observable<AccountInfo> observable) {
        observable.flatMap(new Function<AccountInfo, Observable<UserInfo>>() {
            @Override
            public Observable<UserInfo> apply(AccountInfo accountInfo) throws Exception {
                NetworkAPIFactory.addCommParameter("token", accountInfo.getToken());
                SPUtils.put(LoginActivity.this, UserToken, accountInfo.getToken());
                return NetworkAPIFactory.getUserService().userinfo();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new BaseObserver<UserInfo>() {
            @Override
            protected void onSuccess(UserInfo userInfo) {
                CurrentUserManager.getInstance().setUserInfo(userInfo);
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(UserInfo.class.getName(), userInfo);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }

            @Override
            public void onSubscribe(Disposable d) {
                super.onSubscribe(d);
                showLoader("登录中...");
            }

            @Override
            public void onError(Throwable e) {
                onShowError(e);
            }
        });
    }

    @Override
    protected void onShowError(Throwable ex) {
        super.onShowError(ex);
        SPUtils.remove(LoginActivity.this, UserToken);
    }

}
