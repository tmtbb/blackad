package com.yundian.blackcard.android.activity;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.model.SMSCode;
import com.yundian.blackcard.android.view.OnPasswordInputFinish;
import com.yundian.blackcard.android.view.PasswordView;

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
public class SetupPayPassword2Activity extends BaseActivity {

    @BindView(R.id.passwordView)
    protected PasswordView passwordView;
    private SMSCode smsCode;

    @Override
    public int getLayoutId() {
        return R.layout.ac_setup_pay2_password;
    }

    @Override
    protected boolean isShowBackButton() {
        return true;
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("设置支付密码");
        passwordView.setTitleText("请输入支付密码");
        smsCode = (SMSCode) getIntent().getSerializableExtra(ActionConstant.IntentKey.SMS_CODE);
    }

    @Override
    public void initListener() {
        super.initListener();
        passwordView.setOnFinishInput(new OnPasswordInputFinish() {
            @Override
            public void inputFinish(String password) {
                Intent intent = new Intent(context, SetupPayPassword3Activity.class);
                intent.putExtra(ActionConstant.IntentKey.PAY_PASSOWRD, password);
                intent.putExtra(ActionConstant.IntentKey.SMS_CODE, smsCode);
                startActivity(intent);
                finish();
            }
        });
    }
}
