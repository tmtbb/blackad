package com.yundian.blackcard.android.activity;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.model.SMSCode;
import com.yundian.blackcard.android.networkapi.NetworkAPIFactory;
import com.yundian.blackcard.android.view.OnPasswordInputFinish;
import com.yundian.blackcard.android.view.PasswordView;
import com.yundian.comm.networkapi.listener.OnAPIListener;

import butterknife.BindView;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-08 10:03
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class SetupPayPassword3Activity extends BaseActivity {

    @BindView(R.id.passwordView)
    protected PasswordView passwordView;
    private String payPassword;
    private SMSCode smsCode;

    @Override
    public int getLayoutId() {
        return R.layout.ac_setup_pay3_password;
    }

    @Override
    protected boolean isShowBackButton() {
        return true;
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("设置支付密码");
        passwordView.setTitleText("再次输入支付密码");
    }

    @Override
    public void initData() {
        super.initData();
        payPassword = getIntent().getStringExtra(ActionConstant.IntentKey.PAY_PASSOWRD);
        smsCode = (SMSCode) getIntent().getSerializableExtra(ActionConstant.IntentKey.SMS_CODE);
    }

    @Override
    public void initListener() {
        super.initListener();
        passwordView.setOnFinishInput(new OnPasswordInputFinish() {
            @Override
            public void inputFinish(String password) {
                if (password.equals(payPassword)) {
                    NetworkAPIFactory.getUserService().rePayPassword(payPassword, smsCode.getPhoneNum(), smsCode.getPhoneCode(), smsCode.getCodeToken(), new OnAPIListener<Object>() {
                        @Override
                        public void onError(Throwable ex) {
                            onShowError(ex);
                            finish();
                        }

                        @Override
                        public void onSuccess(Object o) {
                            showToast("支付密码设置成功");
                            finish();
                        }
                    });
                    finish();
                } else {
                    showToast("两次密码不一致");
                }
            }
        });
    }
}
