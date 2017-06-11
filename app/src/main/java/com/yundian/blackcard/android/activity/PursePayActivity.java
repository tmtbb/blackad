package com.yundian.blackcard.android.activity;

import android.content.Intent;
import android.view.View;

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
public class PursePayActivity extends BaseActivity {

    @BindView(R.id.passwordView)
    protected PasswordView passwordView;

    private String phoneNum;

    @Override
    public int getLayoutId() {
        return R.layout.ac_purse_pay;
    }

    @Override
    protected boolean isShowBackButton() {
        return true;
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("输入支付密码");
        passwordView.setTitleText("请输入支付密码");
        passwordView.getForgetPasswordText().setVisibility(View.VISIBLE);
        phoneNum = getIntent().getStringExtra(ActionConstant.IntentKey.PHONE);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initListener() {
        super.initListener();
        passwordView.getForgetPasswordText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SetupPayPasswordActivity.class);
                intent.putExtra(ActionConstant.IntentKey.PHONE, phoneNum);
                startActivity(intent);
                finish();
            }
        });
        passwordView.setOnFinishInput(new OnPasswordInputFinish() {
            @Override
            public void inputFinish(String password) {
                Intent intent = new Intent();
                intent.putExtra(ActionConstant.IntentKey.PAY_PASSWORD, password);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
